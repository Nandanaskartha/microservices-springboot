package com.nandana.quiz_service.model;

import lombok.Data;

@Data
public class QuizDto {
    private String category;
    private int numQuestions;
    private String quizTitle;
}
