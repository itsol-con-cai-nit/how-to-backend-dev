package com.github.truongbb.springbootgraphql.controller;

import com.github.truongbb.springbootgraphql.entity.Author;
import com.github.truongbb.springbootgraphql.entity.Book;
import com.github.truongbb.springbootgraphql.service.AuthorService;
import com.github.truongbb.springbootgraphql.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BookController {

    BookService bookService;
    AuthorService authorService;

    @QueryMapping
    public Book bookById(@Argument String id) {
        return bookService.findById(id);
    }

    @QueryMapping
    public List<Book> books() {
        return bookService.getAll();
    }

    @SchemaMapping
    public Author author(Book book) {
        return authorService.findById(book.getAuthorId());
    }

}
