package com.github.truongbb.springbootgraphql.service;

import com.github.truongbb.springbootgraphql.entity.Author;
import com.github.truongbb.springbootgraphql.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository authorRepository;

    @Override
    public List<Author> getAll() {
        return authorRepository.getAll();
    }

    @Override
    public Author findById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author createAuthor(Author author) {
        return authorRepository.createAuthor(author);
    }

    @Override
    public Author createAuthor(String firstName, String lastName) {
        return authorRepository.createAuthor(new Author(firstName, lastName));
    }
}
