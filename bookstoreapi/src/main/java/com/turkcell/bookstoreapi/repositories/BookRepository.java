package com.turkcell.bookstoreapi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.turkcell.bookstoreapi.models.Book;
import java.util.List;


@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    
    List<Book> findTop10ByNumPagesOrderByNumPagesDesc(int numPages);
    List<Book> findByTitle(String title);
    

}
