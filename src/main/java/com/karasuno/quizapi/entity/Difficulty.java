package com.karasuno.quizapi.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz_difficulty")
@Getter @Setter
@RequiredArgsConstructor
public class Difficulty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String level;

    @OneToMany(mappedBy = "difficulty", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private List<Question> questions;

    public void addQuestion(Question question) {
        if (questions == null) questions = new ArrayList<>();
        questions.add(question);
        question.setDifficulty(this);
    }
}
