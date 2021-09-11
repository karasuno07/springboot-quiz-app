package com.karasuno.quizapi.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Getter @Setter
@EqualsAndHashCode
@ToString
public class UserCourseId implements Serializable {

    private int userId;

    private int courseId;

}
