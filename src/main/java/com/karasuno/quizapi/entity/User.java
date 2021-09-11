package com.karasuno.quizapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = false)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String image;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserCourse> usersCourses;

}
