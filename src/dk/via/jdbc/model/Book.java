package dk.via.jdbc.model;

import java.util.Objects;

public class Book {
    private final String isbn;
    private final String title;
    private final int yearOfPublishing;
    private final Author author;

    public Book(String isbn, String title, int yearOfPublishing, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.yearOfPublishing = yearOfPublishing;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public int getYearOfPublishing() {
        return yearOfPublishing;
    }

    public Author getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return yearOfPublishing == book.yearOfPublishing && isbn.equals(book.isbn) && title.equals(book.title) && author.equals(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, yearOfPublishing, author);
    }
}
