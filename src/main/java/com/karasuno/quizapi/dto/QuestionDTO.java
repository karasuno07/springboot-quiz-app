package com.karasuno.quizapi.dto;

import com.karasuno.quizapi.entity.Answers;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QuestionDTO {

    private int id;

    private String tagName;

    private String difficultyLevel;

    private String question;

    private Answers answers;

    private char correctAnswer;
}
