package dk.via.jdbc.model;

public class Book {
    private String isbn;
    private String title;
    private int yearOfPublishing;
    private Author author;

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
}
