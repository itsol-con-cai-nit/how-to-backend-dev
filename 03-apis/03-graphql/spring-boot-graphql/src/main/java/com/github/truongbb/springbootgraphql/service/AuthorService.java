package com.github.truongbb.springbootgraphql.service;

import com.github.truongbb.springbootgraphql.entity.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAll();

    Author findById(String id);

    Author createAuthor(Author author);

    Author createAuthor(String firstName, String lastName);

}
