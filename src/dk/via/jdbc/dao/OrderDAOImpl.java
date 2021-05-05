package dk.via.jdbc.dao;

import dk.via.jdbc.model.Author;
import dk.via.jdbc.model.Book;
import dk.via.jdbc.model.Order;
import dk.via.jdbc.model.OrderLine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private static OrderDAOImpl instance;

    private OrderDAOImpl() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    public static synchronized OrderDAOImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new OrderDAOImpl();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=jdbc", "postgres", "admin");
    }

    @Override
    public Order createOrder(String name, String address, List<OrderLine> orderLines) throws SQLException {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false); // Also begins new transaction
            PreparedStatement orderStatement = connection.prepareStatement("INSERT INTO Orders(customer, address) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            orderStatement.setString(1, name);
            orderStatement.setString(2, address);
            orderStatement.executeUpdate();
            ResultSet generatedKeys = orderStatement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new SQLException("No keys generated");
            }
            int invoiceNo = generatedKeys.getInt(1);
            Order order = new Order(invoiceNo, name, address);
            PreparedStatement lineStatement = connection.prepareStatement("INSERT INTO OrderLine(invoice_no, isbn, amount) VALUES (?, ?, ?)");
            for(OrderLine line: orderLines) {
                lineStatement.setInt(1, order.getInvoiceNo());
                lineStatement.setString(2, line.getBook().getIsbn());
                lineStatement.setInt(3, line.getAmount());
                lineStatement.executeUpdate();
                order.addOrderLine(line);
            }
            connection.commit();
            return order;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }

    @Override
    public List<Order> getOrders() throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM Orders " +
                "JOIN OrderLine USING(invoice_no) " +
                "JOIN Book USING(isbn) " +
                "JOIN Author ON Book.author_id = Author.id " +
                "ORDER BY invoice_no");
            ResultSet rs = statement.executeQuery();
            ArrayList<Order> orders = new ArrayList<>();
            Order currentOrder = null;
            while(rs.next()) {
                int invoiceNo = rs.getInt("invoice_no");
                if (currentOrder == null || invoiceNo != currentOrder.getInvoiceNo()) {
                    String customer = rs.getString("customer");
                    String address = rs.getString("address");
                    currentOrder = new Order(invoiceNo, customer, address);
                    orders.add(currentOrder);
                }
                Book book = BookDAOImpl.createBook(rs);
                int amount = rs.getInt("amount");
                currentOrder.addOrderLine(book, amount);
            }
            return orders;
        }
    }

    @Override
    public void deleteOrder(Order order) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement lineStatement = connection.prepareStatement("DELETE FROM OrderLine WHERE invoice_no = ?");
            lineStatement.setInt(1, order.getInvoiceNo());
            lineStatement.executeUpdate();
            PreparedStatement orderStatement = connection.prepareStatement("DELETE FROM Orders WHERE invoice_no = ?");
            orderStatement.setInt(1, order.getInvoiceNo());
            orderStatement.executeUpdate();
        }
    }
}
