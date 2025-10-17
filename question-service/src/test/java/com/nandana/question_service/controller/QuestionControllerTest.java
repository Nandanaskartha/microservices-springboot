package com.nandana.question_service.controller;

import com.nandana.question_service.model.Question;
import com.nandana.question_service.model.QuestionWrapper;
import com.nandana.question_service.model.Responses;
import com.nandana.question_service.service.QuestionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionControllerTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    private Question q1;
    private Question q2;
    private QuestionWrapper qw1;
    private QuestionWrapper qw2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        q1 = new Question();
        q1.setCategory("Math");
        q1.setDifficultylevel("Easy");
        q1.setQuestiontitle("What is 2+2?");
        q1.setOption1("2");
        q1.setOption2("3");
        q1.setOption3("4");
        q1.setOption4("1");
        q1.setRightanswer("4");

        q2 = new Question();
        q2.setCategory("Math");
        q2.setDifficultylevel("Easy");
        q2.setQuestiontitle("What is 4-2?");
        q2.setOption1("2");
        q2.setOption2("3");
        q2.setOption3("4");
        q2.setOption4("1");
        q2.setRightanswer("2");
    }

    @Test
    void getAllQuestions() {
        List<Question> allQuestions = Arrays.asList(q1,q2);
        when(questionService.getAllQuestions()).thenReturn(ResponseEntity.ok(allQuestions));
        ResponseEntity<List<Question>> responses = questionController.getAllQuestions();
        assertEquals(allQuestions, responses.getBody());
        assertEquals(HttpStatus.OK, responses.getStatusCode());
        verify(questionService, times(1)).getAllQuestions();
    }

    @Test
    void getQuestionByCategory() {
        String category = "Math";
        List<Question> mathQuestions = Arrays.asList(q1,q2);
        when(questionService.getQuestionsByCategory(category)).thenReturn(ResponseEntity.ok(mathQuestions));
        ResponseEntity<List<Question>> responses = questionController.getQuestionByCategory(category);
        assertEquals(mathQuestions, responses.getBody());
        assertEquals(HttpStatus.OK, responses.getStatusCode());
        verify(questionService, times(1)).getQuestionsByCategory(category);
    }

    @Test
    void addQuestion() {
        when(questionService.addQuestion(q1)).thenReturn(ResponseEntity.ok("Success"));
        ResponseEntity<String> responses = questionController.addQuestion(q1);
        assertEquals("Success", responses.getBody());
        assertEquals(HttpStatus.OK, responses.getStatusCode());
        verify(questionService, times(1)).addQuestion(q1);
    }

    @Test
    void getQuestionsforQuiz() {
        String category="Math";
        int numQ=2;
        List<Integer> questionIds= Arrays.asList(1,2);
        when(questionService.getQuestionsforQuiz(category, numQ)).thenReturn(ResponseEntity.ok(questionIds));
        ResponseEntity<List<Integer>> responses = questionController.getQuestionsforQuiz(category, numQ);
        assertEquals(questionIds, responses.getBody());
        assertEquals(HttpStatus.OK, responses.getStatusCode());
        verify(questionService, times(1)).getQuestionsforQuiz(category, numQ);
    }

    @Test
    void getQuestions() {
        qw1 = new QuestionWrapper(q1.getQuestionid(),q1.getQuestiontitle(),q1.getOption1(),q1.getOption2(),q1.getOption3(),q1.getOption4());
        qw2 = new QuestionWrapper(q2.getQuestionid(),q2.getQuestiontitle(),q2.getOption1(),q2.getOption2(),q2.getOption3(),q2.getOption4());
        List<Integer> questionIds= Arrays.asList(1,2);
        List<QuestionWrapper> qws = Arrays.asList(qw1, qw2);
        when(questionService.getQuestions(questionIds)).thenReturn(ResponseEntity.ok(qws));
        ResponseEntity<List<QuestionWrapper>> responses = questionController.getQuestions(questionIds);
        assertEquals(qws, responses.getBody());
        assertEquals(HttpStatus.OK, responses.getStatusCode());
        verify(questionService, times(1)).getQuestions(questionIds);
    }

    @Test
    void calculateScore() {
        int toReturn=2;
        List<Responses> responses = Arrays.asList(new Responses(1,"1"), new Responses(2,"2"));
        when(questionService.getScoreFromResponses(responses)).thenReturn(ResponseEntity.ok(toReturn));
        ResponseEntity<Integer> score = questionController.calculateScore(responses);
        assertEquals(toReturn, score.getBody());
        assertEquals(HttpStatus.OK, score.getStatusCode());
        verify(questionService, times(1)).getScoreFromResponses(responses);
    }
}