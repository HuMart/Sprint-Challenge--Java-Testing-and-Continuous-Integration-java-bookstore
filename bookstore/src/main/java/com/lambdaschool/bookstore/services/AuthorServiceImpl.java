package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.models.Author;
import com.lambdaschool.bookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "authorService")
public class AuthorServiceImpl implements AuthorService
{
    @Autowired
    private AuthorRepository authorRepos;

    @Override
    public List<Author> findAll(Pageable pageable)
    {
        List<Author> list = new ArrayList<>();
        authorRepos.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Author findAuthorById(long id) throws EntityNotFoundException
    {
        return authorRepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
    }

    @Override
    public void delete(long id) throws EntityNotFoundException
    {
        if (authorRepos.findById(id).isPresent())
        {
            authorRepos.deleteById(id);
        } else
        {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public Author save(Author author)
    {
        Author newAuthor = new Author();

        newAuthor.setFname(author.getFname());
        newAuthor.setLname(author.getLname());

        return authorRepos.save(newAuthor);
    }

    @Transactional
    @Override
    public Author update(Author author, long id)
    {
        Author currentAuthor = authorRepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));

        if(author.getFname() != null)
        {
            currentAuthor.setFname(author.getFname());
        }

        if(author.getLname() != null)
        {
            currentAuthor.setLname(author.getLname());
        }


        return authorRepos.save(currentAuthor);
    }
}