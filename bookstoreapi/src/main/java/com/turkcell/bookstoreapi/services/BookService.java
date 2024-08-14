package com.turkcell.bookstoreapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.turkcell.bookstoreapi.dtos.BookDto;
import com.turkcell.bookstoreapi.models.Book;
import com.turkcell.bookstoreapi.repositories.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    // Save İşlemi
    public BookDto createBook(BookDto bookDto) {
        var book =  convertToModel(bookDto);
        bookRepository.save(book);
        return convertToDto(book);
    }

    // ilk 10 adet result getir.
    public List<BookDto> getAllBooks() {
        return bookRepository.findByTitle("CUENTO A LA VIDA").stream()
        .map(this::convertToDto).toList();
    }

    public BookDto updateBook(String id,BookDto bookDto){
        if(bookRepository.existsById(id)){
            var book = convertToModel(bookDto);
            bookRepository.save(book);
            return convertToDto(book);
        }

        return null;
    }

    public boolean deleteBook(String id){
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public Optional<BookDto> getBookById(String id){
        return bookRepository.findById(id).map(this::convertToDto);
    }

    private BookDto convertToDto(Book book){
        return new BookDto(
            book.getId(),
            book.getTitle(),
            book.getDescription(),
            book.getGender(),
            book.getYearEdition(),
            book.getNumPages(),
            book.getEditorial(),
            book.getIsbn(),
            book.getDateEdition(),
            book.getWriter(),
            book.getImage(),
            book.getTags(),
            book.getCategoryIds().stream().map(ObjectId::toString).collect(Collectors.toList())
            );
        
    }

    private Book convertToModel(BookDto book){
        return new Book(
            book.getId(),
            book.getTitle(),
            book.getDescription(),
            book.getGender(),
            book.getYearEdition(),
            book.getNumPages(),
            book.getEditorial(),
            book.getIsbn(),
            book.getDateEdition(),
            book.getWriter(),
            book.getImage(),
            book.getTags(),
            book.getCategoryIds().stream().map(ObjectId::new).collect(Collectors.toList())
            );
        
    }

}
