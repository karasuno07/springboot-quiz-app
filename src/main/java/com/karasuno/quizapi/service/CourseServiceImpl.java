package com.karasuno.quizapi.service;

import com.karasuno.quizapi.dao.CategoryRepository;
import com.karasuno.quizapi.dao.CourseRepository;
import com.karasuno.quizapi.dao.QuestionRepository;
import com.karasuno.quizapi.entity.Category;
import com.karasuno.quizapi.entity.Course;
import com.karasuno.quizapi.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    private CourseRepository courseRepository;

    private QuestionRepository questionRepository;

    private CategoryRepository categoryRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, QuestionRepository questionRepository, CategoryRepository categoryRepository) {
        this.courseRepository = courseRepository;
        this.questionRepository = questionRepository;
        this.categoryRepository = categoryRepository;
    }

    /* Course Service */
    @Override
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findAllCoursesByUserId(int userId) {
        return courseRepository.findByUserId(userId);
    }


    @Override
    public Course findCourseById(int id) {
        Course course = null;

        Optional<Course> optional = courseRepository.findById(id);
        if (optional.isPresent()) {
            course = optional.get();
        }

        return course;
    }

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(int id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Course> findAllCoursesByCategoryId(int id) {
        return courseRepository.findByCategoryId(id);
    }

    @Override
    public List<Course> findByKeyword(String keyword) {
        return courseRepository.findByTitleLike(keyword);
    }

    /* Quiz Service */
    @Override
    public List<Question> getQuizByCourseId(int id) {
        return questionRepository.findAllByCourseId(id);
    }

    @Override
    public List<Question> getQuizByCourseIdAndTagName(int id, String tag) {
        return questionRepository.findAllByCourseIdAndTagName(id, tag);
    }

    @Override
    public List<Question> getQuizByCourseIdAndDifficultyLevel(int id, String level) {
        return questionRepository.findAllByCourseIdAndDifficultyLevel(id, level);
    }

    @Override
    public List<Question> getQuizByCourseIdAndTagNameAndDifficultyLevel(int id, String tag, String level) {
        return questionRepository.findAllByCourseIdAndTagNameAndDifficultyLevel(id, tag, level);
    }

}
