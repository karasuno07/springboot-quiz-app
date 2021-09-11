package com.karasuno.quizapi.dto;

import com.karasuno.quizapi.entity.Category;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface RecentCourses {

    int getId();

    String getTitle();

    String getDescription();

    @Value("#{target.category}")
    Category getCategory();

    String getImage();

    @Value("#{target.userCourse.purchasedDate}")
    Date getPurchasedDate();

    @Value("#{target.userCourse.recentActiveDate}")
    Date recentActiveDate();
}
