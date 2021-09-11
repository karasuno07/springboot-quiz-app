package com.karasuno.quizapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CourseDTO {

    private int id;

    private String title;

    private String description;

    private CategoryDTO category;

    private String image;
}
