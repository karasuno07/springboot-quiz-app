package com.karasuno.quizapi.service;

import com.karasuno.quizapi.entity.Category;
import com.karasuno.quizapi.entity.Course;
import com.karasuno.quizapi.entity.Question;

import java.util.List;

public interface CourseService {

    /* Course Service */
    public List<Course> findAllCourses();

    public List<Course> findAllCoursesByUserId(int userId);

    public Course findCourseById(int id);

    public void saveCourse(Course course);

    public void deleteCourse(int id);

    public List<Category> findAllCategories();

    public List<Course> findAllCoursesByCategoryId(int id);

    public List<Course> findByKeyword(String keyword);

    /*========================================*/

    /* Quiz Service */
    public List<Question> getQuizByCourseId(int id);

    public List<Question> getQuizByCourseIdAndTagName(int id, String tag);

    public List<Question> getQuizByCourseIdAndDifficultyLevel(int id, String level);

    public List<Question> getQuizByCourseIdAndTagNameAndDifficultyLevel(int id, String tag, String level);
}
