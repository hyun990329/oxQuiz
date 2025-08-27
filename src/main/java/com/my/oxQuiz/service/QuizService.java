package com.my.oxQuiz.service;

import com.my.oxQuiz.entity.QuizEntity;
import com.my.oxQuiz.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuizService {
    @Autowired
    QuizRepository repository;

    public Iterable<QuizEntity> selectAll() {
        return repository.findAll();
    }

    public Optional<QuizEntity> selectOneRandomQuiz() {
        Integer randomId = repository.getRandomId();
        System.out.println(Optional.empty());
        if (randomId == null) {
            return Optional.empty();
        }
        return repository.findById(randomId);
    }

    public Boolean checkQuiz(Integer id, Boolean myAnswer) {
        boolean check = false;
        Optional<QuizEntity> optQuiz = repository.findById(id);
        if(optQuiz.isPresent()) {
            QuizEntity quiz = optQuiz.get();
            if(quiz.isAnswer() == Boolean.parseBoolean(String.valueOf(myAnswer)))
                check = true;
        }
        return check;
    }

    public void insertQuiz(QuizEntity quiz) {
        repository.save(quiz);
    }

    public void updateQuiz(QuizEntity quiz) {
        repository.save(quiz);
    }

    public void deleteQuizById(Integer id) {
        repository.deleteById(id);
    }
}
