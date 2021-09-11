package com.karasuno.quizapi.dao;

import com.karasuno.quizapi.entity.UserCourseId;
import com.karasuno.quizapi.entity.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface UserCourseRepository extends JpaRepository<UserCourse, UserCourseId> {

    @Query("select uc from UserCourse uc where uc.course.id in (select c.course.id from UserCourse c where c.user.id = :user_id)")
    List<UserCourse> findByUserId(@Param("user_id") int id);

    UserCourse findByUserIdAndCourseId(int userId, int courseId);
}
