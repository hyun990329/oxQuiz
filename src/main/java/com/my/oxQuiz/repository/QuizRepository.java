package com.my.oxQuiz.repository;

import com.my.oxQuiz.entity.QuizEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends CrudRepository<QuizEntity, Integer> {
    @Query(value = "SELECT id FROM quiz ORDER BY RAND() limit 1", nativeQuery = true)
    Integer getRandomId();
}
