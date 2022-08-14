package com.github.truongbb.springbootgraphql.repository;

import com.github.truongbb.springbootgraphql.entity.Author;

import java.util.List;

public interface AuthorRepository {

    List<Author> getAll();

    Author findById(String id);

    Author createAuthor(Author author);

}
