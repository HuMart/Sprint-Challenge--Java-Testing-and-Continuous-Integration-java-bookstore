package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.repository.AuthorRepository;
import com.lambdaschool.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "bookService")
public class BookServiceImpl implements BookService
{
    @Autowired
    private BookRepository bookrepos;

    @Override
    public List<Book> findAll(Pageable pageable)
    {
        List<Book> list = new ArrayList<>();
        bookrepos.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Book findBookById(long id) throws EntityNotFoundException
    {
        return bookrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
    }

    @Override
    public void delete(long id) throws EntityNotFoundException
    {
        if (bookrepos.findById(id).isPresent())
        {
            bookrepos.deleteById(id);
        } else
        {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public Book save(Book book)
    {
        Book newBook = new Book();

        newBook.setTitle(book.getTitle());
        newBook.setISBN(book.getISBN());
        newBook.setCopy(book.getCopy());
        newBook.setSectionid(book.getSectionid());

        return bookrepos.save(newBook);
    }

    @Transactional
    @Override
    public Book update(Book book, long id)
    {
        Book currentBook = bookrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));

        if(book.getTitle() != null)
        {
            currentBook.setTitle(book.getTitle());
        }

        if(book.getISBN() != null)
        {
            currentBook.setISBN(book.getISBN());
        }

        if(book.getCopy() != 5000)
        {
            currentBook.setCopy(book.getCopy());
        }

        if (book.getSectionid() != 5000)
        {
            currentBook.setSectionid(book.getSectionid());
        }

        return bookrepos.save(currentBook);
    }
}