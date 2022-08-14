package com.github.truongbb.springbootgraphql.repository;

import com.github.truongbb.springbootgraphql.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private static List<Book> books = Arrays.asList(
            new Book("book-1", "Harry Potter and the Philosopher's Stone", 223, "author-1"),
            new Book("book-2", "Moby Dick", 635, "author-2"),
            new Book("book-3", "Interview with the vampire", 371, "author-3")
    );

    @Override
    public List<Book> getAll() {
        return books;
    }

    @Override
    public Book findById(String id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);
    }

}
