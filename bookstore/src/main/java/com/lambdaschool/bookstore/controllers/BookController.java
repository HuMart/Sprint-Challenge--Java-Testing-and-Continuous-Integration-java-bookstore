package com.lambdaschool.bookstore.controllers;

import com.lambdaschool.bookstore.models.Author;
import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.models.ErrorDetail;
import com.lambdaschool.bookstore.services.BookService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController
{
    @Autowired
    private BookService bookService;


    @ApiOperation(value = "Returns all books", response = Book.class, responseContainer = "List")
    @ApiImplicitParams({
                               @ApiImplicitParam(name = "page", dataType = "integr", paramType = "query",
                                                 value = "Results page you want to retrieve (0..N)"),
                               @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                                                 value = "Number of records per page."),
                               @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                                                 value = "Sorting criteria in the format: property(,asc|desc). " +
                                                         "Default sort order is ascending. " +
                                                         "Multiple sort criteria are supported.")
                       })
    @GetMapping(value = "/books", produces = {"application/json"})
    public ResponseEntity<?> listAllBooks(@PageableDefault(page = 0, size = 2) Pageable pageable)
    {
        List<Book> myBooks = bookService.findAll(pageable);
        return new ResponseEntity<>(myBooks, HttpStatus.OK);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @ApiOperation(value = "Create a new Book", notes = "The newly created Book id will be sent in the location header.", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Book Created Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error creating book", response = ErrorDetail.class)
    })
    @PostMapping(value = "/book",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewBook(@Valid
                                        @RequestBody
                                                Book newBook) throws URISyntaxException
    {
        newBook = bookService.save(newBook);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newBookURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{bookid}").buildAndExpand(newBook.getBookid()).toUri();
        responseHeaders.setLocation(newBookURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @ApiOperation(value = "Update a book", response = void.class)
    @ApiResponses({
                          @ApiResponse(code = 200, message = "Book updated Successfully", response = void.class),
                          @ApiResponse(code = 500, message = "Error updating Book", response = ErrorDetail.class)
                  })
    @PutMapping(value = "/book/{bookid}")
    public ResponseEntity<?> updateBook(
            @RequestBody
                    Book updateBook,
            @PathVariable
                    long bookid)
    {
        bookService.update(updateBook, bookid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//
//
//    @ApiOperation(value = "Delete a Book", response = void.class)
//    @ApiResponses({
//                          @ApiResponse(code = 200, message = "Book deleted Successfully", response = void.class),
//                          @ApiResponse(code = 500, message = "Error deleting Book", response = ErrorDetail.class)
//                  })
//    @DeleteMapping("/book/{bookid}")
//    public ResponseEntity<?> deleteBookById(
//            @PathVariable
//                    long bookid)
//    {
//        bookService.delete(bookid);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}