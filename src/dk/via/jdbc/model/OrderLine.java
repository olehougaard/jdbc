package dk.via.jdbc.model;

import java.util.Objects;

public class OrderLine {
    private final Book book;
    private final int amount;

    public OrderLine(Book book, int amount) {
        this.book = book;
        this.amount = amount;
    }

    public Book getBook() {
        return book;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLine orderLine = (OrderLine) o;
        return amount == orderLine.amount && book.equals(orderLine.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, amount);
    }
}
