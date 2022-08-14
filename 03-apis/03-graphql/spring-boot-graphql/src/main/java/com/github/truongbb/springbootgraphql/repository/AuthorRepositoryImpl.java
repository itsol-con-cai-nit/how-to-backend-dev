package com.github.truongbb.springbootgraphql.repository;

import com.github.truongbb.springbootgraphql.entity.Author;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    private static List<Author> authors = Arrays.asList(
            new Author("author-1", "Joanne", "Rowling"),
            new Author("author-2", "Herman", "Melville"),
            new Author("author-3", "Anne", "Rice")
    );

    @Override
    public List<Author> getAll() {
        return authors;
    }

    @Override
    public Author findById(String id) {
        return authors.stream().filter(author -> author.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Author createAuthor(Author author) {
        authors = new ArrayList<>(authors);
        authors.add(author);
        return author;
    }

}
