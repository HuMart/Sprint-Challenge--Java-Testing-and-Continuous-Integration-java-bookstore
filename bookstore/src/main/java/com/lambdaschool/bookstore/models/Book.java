package com.lambdaschool.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
public class Book extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bookid;

    private String title;

    private String ISBN;

    private int copy;

    private long sectionid;


    @ManyToMany
    @JoinTable(name = "wrote",
               joinColumns = {@JoinColumn(name = "bookid")},
               inverseJoinColumns = {@JoinColumn(name = "authorid")})
    @JsonIgnoreProperties("authors")
    private List<Author> authors = new ArrayList<>();

    public Book()
    {
    }

    public Book(String title, String ISBN, int copy, long sectionid)
    {
        this.title = title;
        this.ISBN = ISBN;
        this.copy = copy;
        this.sectionid = sectionid;
    }

    public long getBookid()
    {
        return bookid;
    }

    public void setBookid(long bookid)
    {
        this.bookid = bookid;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getISBN()
    {
        return ISBN;
    }

    public void setISBN(String ISBN)
    {
        this.ISBN = ISBN;
    }

    public int getCopy()
    {
        return copy;
    }

    public void setCopy(int copy)
    {
        this.copy = copy;
    }

    public long getSectionid()
    {
        return sectionid;
    }

    public void setSectionid(long sectionid)
    {
        this.sectionid = sectionid;
    }

    public List<Author> getAuthors()
    {
        return authors;
    }

    public void setAuthors(List<Author> authors)
    {
        this.authors = authors;
    }
}