package com.github.truongbb.springbootgraphql.service;

import com.github.truongbb.springbootgraphql.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();

    Book findById(String id);

}
