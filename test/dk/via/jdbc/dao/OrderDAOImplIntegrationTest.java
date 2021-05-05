package dk.via.jdbc.dao;

import dk.via.jdbc.model.Author;
import dk.via.jdbc.model.Book;
import dk.via.jdbc.model.Order;
import dk.via.jdbc.model.OrderLine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
Note: This test assumes the database is set up according to /etc/model.sql.
In particular, the books inserted in that script should exist, and no book
should exist with ISBN 1111111111111.
It is assumed the database is running on localhost:5432 and that the postgres user has password 'admin'.
 */
public class OrderDAOImplIntegrationTest {
    private Order order;
    private OrderDAO dao;
    private Author connolly;
    private Author hawthorne;
    private Book dbs;
    private Book scarlet;

    @BeforeEach
    public void setUp() throws SQLException {
        dao = OrderDAOImpl.getInstance();
        connolly = new Author(1, "Thomas Connolly");
        hawthorne = new Author(2, "Nathaniel Hawthorne");
        dbs = new Book("9780321523068", "Database Systems 5th Ed.", 2010, connolly);
        scarlet = new Book("9780142437261", "The Scarlet Letter: A Romance", 2003, hawthorne);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (order != null) {
            dao.deleteOrder(order);
            order = null;
        }
    }

    @Test
    public void aCorrectOrderIsInsertedToTheDatabase() throws SQLException {
        List<OrderLine> lines = Arrays.asList(new OrderLine(dbs, 3), new OrderLine(scarlet, 1));
        order = dao.createOrder("John Doe", "Null Island", lines);
        List<Order> orders = dao.getOrders();
        assertTrue(orders.contains(order));
    }

    @Test
    public void anIncorrectOrderIsntInsertedAndAnExceptionIsThrown() throws SQLException {
        Book noBook = new Book("1111111111", "Not a book", -1, hawthorne);
        List<OrderLine> lines = Arrays.asList(new OrderLine(dbs, 3), new OrderLine(noBook, 1));
        List<Order> existingOrders = dao.getOrders();
        assertThrows(SQLException.class, () -> dao.createOrder("John Doe", "Null Island", lines));
        assertEquals(existingOrders.size(), dao.getOrders().size());
    }
}
