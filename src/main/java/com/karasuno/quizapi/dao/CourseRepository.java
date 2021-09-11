package com.karasuno.quizapi.dao;

import com.karasuno.quizapi.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@RepositoryRestResource(exported = true, path = "courses-repository")
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("from Course c join fetch c.usersCourses uc where c.id in (select course.id from UserCourse where user.id = :user_id)")
    List<Course> findByUserId(@Param("user_id") int id);

    List<Course> findByCategoryId(int id);

    List<Course> findByTitleLike(String title);

}
