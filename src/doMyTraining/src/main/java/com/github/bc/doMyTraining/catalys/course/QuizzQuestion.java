package com.github.bc.doMyTraining.catalys.course;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class QuizzQuestion {

    private String quizzId;
    private String question;
    private List<String> badAnswers = new ArrayList<>();
    private String answer;
    private int hashcode;

    public QuizzQuestion(String quizzId, String question) {
        this.question = question;
        this.quizzId = quizzId;
        this.hashcode = question.hashCode();
    }

    @JsonCreator
    public QuizzQuestion(
            @JsonProperty("quizzId") String quizzId,
            @JsonProperty("question") String question,
            @JsonProperty("badAnswers") List<String> badAnswers,
            @JsonProperty("answer") String answer) {
        this(quizzId, question);
        this.badAnswers = badAnswers;
        this.answer = answer;
    }

    public String getQuizzId() {
        return quizzId;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getBadAnswers() {
        return badAnswers;
    }

    public String getAnswer() {
        return answer;
    }

    public int getHashcode() {
        return this.hashcode;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void addBadAnswer(String badAnswer) {
        this.badAnswers.add(badAnswer);
    }
}
