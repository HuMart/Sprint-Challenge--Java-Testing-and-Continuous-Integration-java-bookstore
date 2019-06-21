package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.models.Author;
import com.lambdaschool.bookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "authorService")
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository repo;


    @Override
    public List<Author> findAll(Pageable pageable) {
        List<Author> authorList = new ArrayList<>();
        repo.findAll(pageable).iterator().forEachRemaining(authorList::add);
        return authorList;
    }

    @Override
    public void save(Author author) {
        repo.save(author);
    }
}