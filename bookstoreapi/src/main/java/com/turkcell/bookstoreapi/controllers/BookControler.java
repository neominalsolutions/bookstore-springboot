package com.turkcell.bookstoreapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.bookstoreapi.dtos.BookDto;
import com.turkcell.bookstoreapi.services.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/books")
public class BookControler {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(),HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<BookDto> postBook(@RequestBody BookDto dto) {
        
        var book = bookService.createBook(dto);

        return new ResponseEntity<>(book,HttpStatus.CREATED);
    }
    
    
}
