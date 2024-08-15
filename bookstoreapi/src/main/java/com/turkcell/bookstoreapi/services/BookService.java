package com.turkcell.bookstoreapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.VariableOperators.Map;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.turkcell.bookstoreapi.dtos.BookDto;
import com.turkcell.bookstoreapi.dtos.BookWithCategoryDto;
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

    // Bu Aggregation Framework ile çalışırken
    public List<BookWithCategoryDto> getBooksWithCategoryNames(){
        var aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("num_pages").gte(1000)),
            Aggregation.lookup("categories", "category_ids", "_id", "categories"),
            Aggregation.project("title","num_pages").and("categories.name").as("categoryNames")
            );

            var response = mongoTemplate.aggregate(aggregation, "books",BookWithCategoryDto.class);

            return response.getMappedResults();
    }

    // Query işlemleri Filter
    public List<BookDto> findByYearEditionAndNumPagesGreaterThanEqual(int yearEdition,int numPages){
        var query = new Query();
        query.addCriteria(Criteria.where("year_edition").is(yearEdition));
        query.addCriteria(Criteria.where("num_pages").is(numPages));

        // Veritabanına atılan sorgularda document nesneleri ile çalışmamız lazım
        var data = mongoTemplate.find(query, Book.class);
        var dto = data.stream().map(this::convertToDto).toList();

        return dto;
        
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
