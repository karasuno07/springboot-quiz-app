package com.karasuno.quizapi.dao;

import com.karasuno.quizapi.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@RepositoryRestResource(path = "quiz-question", collectionResourceRel = "questions", exported = false)
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findAllByCourseId(int id);

    List<Question> findAllByCourseIdAndTagName(int id, String tag);

    List<Question> findAllByCourseIdAndDifficultyLevel(int id, String level);

    List<Question> findAllByCourseIdAndTagNameAndDifficultyLevel(int id, String tag, String level);
}
