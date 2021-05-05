package dk.via.jdbc.model;

import java.util.Objects;

public class Author {
    private final int id;
    private final String name;

    public Author(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id && name.equals(author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
