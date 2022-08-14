package com.github.truongbb.springbootgraphql.controller;

import com.github.truongbb.springbootgraphql.entity.Author;
import com.github.truongbb.springbootgraphql.service.AuthorService;
import com.github.truongbb.springbootgraphql.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthorController {


    BookService bookService;
    AuthorService authorService;

    @MutationMapping
    public Author createAuthor(@Argument String firstName, @Argument String lastName) {
        return authorService.createAuthor(firstName, lastName);
    }

    @MutationMapping
    public Author createAuthorVer2(@Argument Author input) {
        return authorService.createAuthor(input);
    }

}
