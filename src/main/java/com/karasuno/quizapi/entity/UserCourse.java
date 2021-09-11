package com.karasuno.quizapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users_courses")
@Getter @Setter
public class UserCourse {

    @EmbeddedId
    private UserCourseId id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "courseId", insertable = false, updatable = false)
    private Course course;

    @Column(name = "purchased_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchasedDate;

    @Column(name = "recent_active_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recentActiveDate;

    @Override
    public String toString() {
        return "UserCourse{" +
                "id=" + id +
                ", purchasedDate=" + purchasedDate +
                ", recentActiveDate=" + recentActiveDate +
                '}';
    }
}
