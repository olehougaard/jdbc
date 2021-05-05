DROP SCHEMA jdbc CASCADE;
CREATE SCHEMA jdbc;

SET search_path TO 'jdbc';

CREATE TABLE Author (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
);

CREATE TABLE Book (
                      isbn CHAR(13) PRIMARY KEY,
                      title VARCHAR(100) NOT NULL,
                      yearOfPublishing int NOT NULL CHECK (yearOfPublishing > 1900),
                      author_id INTEGER NOT NULL REFERENCES Author(id)
);

CREATE TABLE Orders (
                        invoice_no SERIAL PRIMARY KEY,
                        customer VARCHAR(100) NOT NULL,
                        address VARCHAR(200) NOT NULL
);

CREATE TABLE OrderLine (
                           invoice_no INTEGER REFERENCES Orders(invoice_no),
                           isbn CHAR(13) REFERENCES Book(isbn),
                           amount int NOT NULL CHECK(amount > 0),
                           PRIMARY KEY(invoice_no, isbn)
);

INSERT INTO Author(name) VALUES ('Thomas Connolly'), ('Nathaniel Hawthorne');

INSERT INTO Book(isbn, title, yearOfPublishing, author_id) VALUES
   ('9780321523068', 'Database Systems 5th Ed.', 2010, 1),
   ('9781405874373', 'Business Database Systems', 2008, 1),
   ('9780142437261', 'The Scarlet Letter: A Romance', 2003, 2);
