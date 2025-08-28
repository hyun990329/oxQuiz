package com.my.oxQuiz.service;

import com.my.oxQuiz.entity.QuizEntity;
import com.my.oxQuiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizService {
    private final QuizRepository repository;

    public List<QuizEntity> selectAll() {
        return (List<QuizEntity>) repository.findAll();
    }

    public Optional<QuizEntity> selectOneRandomQuiz() {
        Integer randomId = repository.getRandomId();
        return randomId != null ? repository.findById(randomId) : Optional.empty();
    }

    public Boolean checkQuiz(Integer id, Boolean myAnswer) {
        return repository.findById(id)
                .map(quiz -> quiz.isAnswer() == myAnswer)
                .orElse(false);
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