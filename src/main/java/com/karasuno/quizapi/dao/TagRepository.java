package com.karasuno.quizapi.dao;

import com.karasuno.quizapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(path = "quiz-tags", collectionResourceRel = "tags", exported = false)
public interface TagRepository extends JpaRepository<Tag, Integer> {
}
