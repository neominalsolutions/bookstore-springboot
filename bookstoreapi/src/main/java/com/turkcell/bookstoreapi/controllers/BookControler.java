package com.turkcell.bookstoreapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.bookstoreapi.dtos.BookDto;
import com.turkcell.bookstoreapi.services.BookService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




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

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable String id) {
        var book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> putBook(@PathVariable String id, @RequestBody BookDto request) {
    
        var response = bookService.updateBook(id, request);
        if(response != null) {
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable String id) {
    
        var isOk = bookService.deleteBook(id);

        if(isOk) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
    
}
