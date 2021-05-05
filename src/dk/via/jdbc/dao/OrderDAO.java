package dk.via.jdbc.dao;

import dk.via.jdbc.model.Order;
import dk.via.jdbc.model.OrderLine;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {
    Order createOrder(String name, String address, List<OrderLine> orderLines) throws SQLException;

    public List<Order> getOrders() throws SQLException;

    void deleteOrder(Order order) throws SQLException;
}
