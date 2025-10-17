package com.nandana.quiz_service.service;

import com.nandana.quiz_service.dao.QuizDao;
import com.nandana.quiz_service.feign.QuizInterface;
import com.nandana.quiz_service.model.QuestionWrapper;
import com.nandana.quiz_service.model.Quiz;
import com.nandana.quiz_service.model.Responses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizServiceTest {

    @Mock
    private QuizDao quizdao;

    @Mock
    private QuizInterface quizInterface;

    @InjectMocks
    private QuizService quizService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createQuiz() {
        List<Integer> questionsList = Arrays.asList(1,2);
        String category="General";
        int numQuestions =2;
        String title = "Dummy Title";
        when(quizInterface.getQuestionsforQuiz(category,numQuestions))
                .thenReturn(ResponseEntity.ok(questionsList));
        // Act
        ResponseEntity<String> response = quizService.createQuiz(category, numQuestions, title);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());

        // Verify Feign + DAO interactions
        verify(quizInterface, times(1)).getQuestionsforQuiz(category, numQuestions);
        verify(quizdao, times(1)).save(any(Quiz.class));

        ArgumentCaptor<Quiz>quizCaptor = ArgumentCaptor.forClass(Quiz.class);
        verify(quizdao, times(1)).save(quizCaptor.capture());
        Quiz savedQuiz = quizCaptor.getValue();
        assertEquals("Dummy Title", savedQuiz.getQuiztitle());
        assertEquals(questionsList, savedQuiz.getQuestions());
    }



    @Test
    void getQuestionsFromQuiz() {
        int quizid=1;
        Quiz dummyquiz = new Quiz();
        dummyquiz.setQuiztitle("Dummy");
        dummyquiz.setQuizid(quizid);
        dummyquiz.setQuestions(Arrays.asList(1,2));
        when(quizdao.findById(quizid)).thenReturn(Optional.of(dummyquiz));
        List<QuestionWrapper> mockQuestions = Arrays.asList(
                new QuestionWrapper(1,"What is 2+2?","2","3","1","4"),
                new QuestionWrapper(2,"What is 4-2?","2","3","1","4")
        );
        when(quizInterface.getQuestions(Arrays.asList(1,2))).thenReturn(ResponseEntity.ok(mockQuestions));
        //ACT
        ResponseEntity<List<QuestionWrapper>> response = quizService.getQuestionsFromQuiz(quizid);

        assertEquals(mockQuestions, response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(quizdao,times(1)).findById(quizid);
        verify(quizInterface, times(1)).getQuestions(Arrays.asList(1,2));
    }

    @Test
    void getResults() {
        int quizid=1, toReturn =2;
        List<Responses> responses=Arrays.asList(new Responses(1,"1"), new Responses(2,"2"));
        when(quizInterface.calculateScore(responses)).thenReturn(ResponseEntity.ok(toReturn));
        ResponseEntity<Integer> response = quizService.getResults(quizid, responses);
        assertEquals(toReturn, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(quizInterface, times(1)).calculateScore(responses);
    }
}