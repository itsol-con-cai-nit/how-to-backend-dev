package com.github.truongbb.springbootgraphql.repository;

import com.github.truongbb.springbootgraphql.entity.Book;

import java.util.List;

public interface BookRepository {

    List<Book> getAll();

    Book findById(String id);

}
