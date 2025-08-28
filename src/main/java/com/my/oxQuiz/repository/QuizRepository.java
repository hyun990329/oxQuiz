package com.my.oxQuiz.repository;

import com.my.oxQuiz.entity.QuizEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends CrudRepository<QuizEntity, Integer> {
    @Query("SELECT q.id FROM QuizEntity q ORDER BY FUNCTION('RAND') LIMIT 1")
    Integer getRandomId();
}