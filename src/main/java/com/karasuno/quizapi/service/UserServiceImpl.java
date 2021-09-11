package com.karasuno.quizapi.service;

import com.karasuno.quizapi.dao.*;
import com.karasuno.quizapi.entity.*;
import com.karasuno.quizapi.rest.handler.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private CourseRepository courseRepository;

    private UserCourseRepository userCourseRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CourseRepository courseRepository, UserCourseRepository userCourseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.userCourseRepository = userCourseRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        User returnValue = null;

        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            returnValue = optional.get();
        }

        return returnValue;
    }

    @Override
    public User findByUsername(String username) {
        User returnValue = null;

        Optional<User> optional = userRepository.findByUsername(username);
        if (optional.isPresent()) {
            returnValue = optional.get();
        }

        return returnValue;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserCourse findByUserIdAndCourseId(int userId, int courseId) {
        return userCourseRepository.findByUserIdAndCourseId(userId, courseId);
    }

    @Override
    public void addCourseToWatchList(int userId, int courseId) {
        UserCourse userCourse = new UserCourse();

        User user = null;
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }

        Course course = null;
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            course = courseOptional.get();
        }

        if (user != null && course != null) {
            UserCourseId id = new UserCourseId();
            id.setUserId(userId);
            id.setCourseId(courseId);

            Optional<UserCourse> optional = userCourseRepository.findById(id);

            if (optional.isPresent()) {
                throw new EntityExistsException("Course already exists in user's watchlist");
            } else {
                userCourse.setId(id);
                userCourse.setPurchasedDate(new Date());
                userCourse.setRecentActiveDate(new Date());

                userCourseRepository.save(userCourse);
            }
        } else {
            if (user == null) throw new NotFoundException("Not found user entity with id: " + userId);
            if (course == null) throw  new NotFoundException("Not found course entity with id: " + courseId);
        }
    }

    @Override
    public void saveUserCourse(UserCourse userCourse) {
        userCourseRepository.save((userCourse));
    }

    @Override
    public void removeCourseFromWatchList(int userId, int courseId) {
        User user = null;

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }

        Course course = null;
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            course = courseOptional.get();
        }

        if (user != null && course != null) {
            UserCourseId id = new UserCourseId();
            id.setUserId(userId);
            id.setCourseId(courseId);

            userCourseRepository.deleteById(id);
        } else {
            if (user == null) throw new NotFoundException("Not found user entity with id: " + userId);
            if (course == null) throw  new NotFoundException("Not found course entity with id: " + courseId);
        }
    }
}
