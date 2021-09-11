package com.karasuno.quizapi.service;

import com.karasuno.quizapi.entity.*;

import java.util.List;

public interface UserService {

    /* User Service */
    public List<User> findAll();

    public User findById(int id);

    public User findByUsername(String username);

    public void save(User user);

    public void delete(int id);

    /* User Courses Service */

    public UserCourse findByUserIdAndCourseId(int userId, int courseId); // to update active date

    public void addCourseToWatchList(int userId, int courseId);

    public void saveUserCourse(UserCourse userCourse);

    public void removeCourseFromWatchList(int userId, int courseId);
}
