package com.turkcell.bookstoreapi.models;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;
    private String title;
    private String description;
    private String gender;
    
    @Field(name="year_edition")
    private int yearEdition;

    @Field(name = "num_pages")
    private int numPages;
    private String editorial;
    private long isbn;

    @Field(name="date_edition")
    private Date dateEdition;
    private String writer;
    private String image;
    private List<String> tags;

    @Field(name="category_ids")
    private List<ObjectId> categoryIds;
}