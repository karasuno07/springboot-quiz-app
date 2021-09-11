package com.karasuno.quizapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class UserCourseDTO {

    private int id;

    private String title;

    private String description;

    private CategoryDTO category;

    private String image;

    private Date purchasedDate;
    
    private Date recentActiveDate;
}
