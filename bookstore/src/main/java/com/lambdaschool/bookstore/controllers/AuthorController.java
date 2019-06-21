package com.lambdaschool.bookstore.controllers;

import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.models.Author;
import com.lambdaschool.bookstore.models.ErrorDetail;
import com.lambdaschool.bookstore.services.AuthorService;
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
@RequestMapping("/authors")
public class AuthorController
{
    @Autowired
    private AuthorService authorService;

    @ApiOperation(value = "Returns all authors", response = Author.class, responseContainer = "List")
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
    @GetMapping(value = "/authors", produces = {"application/json"})
    public ResponseEntity<?> listAllAthors(@PageableDefault(page = 0, size = 2) Pageable pageable)
    {
        List<Author> myAuthors = authorService.findAll(pageable);
        return new ResponseEntity<>(myAuthors, HttpStatus.OK);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @ApiOperation(value = "Create a new author", notes = "The newly created author id will be sent in the location header.", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Author Created Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error creating Author", response = ErrorDetail.class)
    })
    @PostMapping(value = "/author",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewAuthor(@Valid
                                          @RequestBody
                                                  Author newAuthor) throws URISyntaxException
    {
        newAuthor = authorService.save(newAuthor);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newAuthorURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{authorid}").buildAndExpand(newAuthor.getAuthorid()).toUri();
        responseHeaders.setLocation(newAuthorURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @ApiOperation(value = "Update an author", response = void.class)
    @ApiResponses({
                          @ApiResponse(code = 200, message = "Author updated Successfully", response = void.class),
                          @ApiResponse(code = 500, message = "Error updating Author", response = ErrorDetail.class)
                  })
    @PutMapping(value = "/author/{authorid}")
    public ResponseEntity<?> updateAuthor(
            @RequestBody
                    Author updateAuthor,
            @PathVariable
                    long authorid)
    {
        authorService.update(updateAuthor, authorid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


//    @ApiOperation(value = "Delete an Author", response = void.class)
//    @ApiResponses({
//                          @ApiResponse(code = 200, message = "Author deleted Successfully", response = void.class),
//                          @ApiResponse(code = 500, message = "Error deleting Author", response = ErrorDetail.class)
//                  })
//    @DeleteMapping("/author/{authorid}")
//    public ResponseEntity<?> deleteAuthorById(
//            @PathVariable
//                    long authorid)
//    {
//        authorService.delete(authorid);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
