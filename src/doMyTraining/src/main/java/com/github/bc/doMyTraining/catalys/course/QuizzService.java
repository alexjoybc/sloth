package com.github.bc.doMyTraining.catalys.course;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;

public class QuizzService {

    @CachePut(cacheNames = "quizzQuestion", key = "{ #quizzQuestion.quizzId,  #quizzQuestion.question }", cacheManager = "quizzQuestionCacheManager")
    public Optional<QuizzQuestion> put(QuizzQuestion quizzQuestion) {
        return Optional.of(quizzQuestion);
    }

    @Cacheable(cacheNames = "quizzQuestion", key = "{ #quizzId, #question }", cacheManager = "quizzQuestionCacheManager", unless="#result == null")
    public Optional<QuizzQuestion> get(String quizzId, String question) {
        return Optional.empty();
    }

}
