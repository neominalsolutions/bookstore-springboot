package com.turkcell.bookstoreapi.dtos;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String id;
    private String title;
    private String description;
    private String gender;
    

    private int yearEdition;

    private int numPages;
    private String editorial;
    private long isbn;

    private Date dateEdition;
    private String writer;
    private String image;
    private List<String> tags;

    private List<String> categoryIds;
}