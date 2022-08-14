package com.github.truongbb.springbootgraphql.service;

import com.github.truongbb.springbootgraphql.entity.Book;
import com.github.truongbb.springbootgraphql.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;

    @Override
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    public Book findById(String id) {
        return bookRepository.findById(id);
    }

}
