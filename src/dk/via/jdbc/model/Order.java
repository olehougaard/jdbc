package dk.via.jdbc.model;

import java.util.ArrayList;
import java.util.Objects;

public class Order {
    private final int invoiceNo;
    private final String customer;
    private final String address;
    private final ArrayList<OrderLine> lines;

    public Order(int invoiceNo, String customer, String address) {
        this.invoiceNo = invoiceNo;
        this.customer = customer;
        this.address = address;
        this.lines = new ArrayList<>();
    }

    public int getInvoiceNo() {
        return invoiceNo;
    }

    public String getCustomer() {
        return customer;
    }

    public String getAddress() {
        return address;
    }

    public void addOrderLine(Book book, int amount) {
        lines.add(new OrderLine(book, amount));
    }

    public void addOrderLine(OrderLine line) {
        lines.add(line);
    }

    public ArrayList<OrderLine> getLines() {
        return lines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return invoiceNo == order.invoiceNo && customer.equals(order.customer) && address.equals(order.address) && lines.equals(order.lines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceNo, customer, address, lines);
    }
}
