package com.turkcell.bookstoreapi.dtos;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookWithCategoryDto {
    private String id;

    private String title;
   
    private List<String> categoryNames;
}
