package com.github.bc.doMyTraining.catalys.course;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QuizzService {

    @CachePut(cacheNames = "quizzQuestion", key = "{ #quizzQuestion.quizzId,  #quizzQuestion.hashcode }", cacheManager = "quizzQuestionCacheManager")
    public Optional<QuizzQuestion> put(QuizzQuestion quizzQuestion) {
        return Optional.of(quizzQuestion);
    }

    @Cacheable(cacheNames = "quizzQuestion", key = "{ #quizzId, #hashcode }", cacheManager = "quizzQuestionCacheManager", unless="#result == null")
    public Optional<QuizzQuestion> get(String quizzId, int hashcode) {
        return Optional.empty();
    }

}
