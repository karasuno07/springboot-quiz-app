package com.karasuno.quizapi.dao;

import com.karasuno.quizapi.entity.Answers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(path = "quiz-answers", collectionResourceRel = "answers", exported = false)
public interface AnswersRepository extends JpaRepository<Answers, Integer> {
}
