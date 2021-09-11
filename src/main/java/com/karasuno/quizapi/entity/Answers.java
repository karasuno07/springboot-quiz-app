package com.karasuno.quizapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "quiz_answers")
@Getter @Setter
@RequiredArgsConstructor
@JsonPropertyOrder({"a", "b", "c", "d"})
public class Answers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @JsonIgnore
    private int id;

    private String A;

    private String B;

    private String C;

    private String D;
}
