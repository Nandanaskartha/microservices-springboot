package com.nandana.quiz_service.controller;
import com.nandana.quiz_service.model.QuestionWrapper;
import com.nandana.quiz_service.model.QuizDto;
import com.nandana.quiz_service.model.Responses;
import com.nandana.quiz_service.service.QuizService;
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

class QuizControllerTest {

    @Mock
    private QuizService quizService;

    @InjectMocks
    private QuizController quizController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateQuiz() {
        // Arrange
        QuizDto quizDto = new QuizDto();
        quizDto.setCategory("Science");
        quizDto.setNumQuestions(5);
        quizDto.setQuizTitle("Basic Science Quiz");

        when(quizService.createQuiz("Science", 5, "Basic Science Quiz"))
                .thenReturn(ResponseEntity.ok("Quiz Created Successfully"));

        // Act
        ResponseEntity<String> response = quizController.createQuiz(quizDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Quiz Created Successfully", response.getBody());
        verify(quizService, times(1))
                .createQuiz("Science", 5, "Basic Science Quiz");
    }

    @Test
    void testGetQuestionsOfQuiz() {
        // Arrange
        int quizId = 1;
        QuestionWrapper q1 = new QuestionWrapper(1, "What is 2+2?","2","3","1","4");
        QuestionWrapper q2 = new QuestionWrapper(2, "What is the color of sky?","pink","green","blue","red");
        List<QuestionWrapper> mockQuestions = Arrays.asList(q1, q2);

        when(quizService.getQuestionsFromQuiz(quizId))
                .thenReturn(ResponseEntity.ok(mockQuestions));

        // Act
        ResponseEntity<List<QuestionWrapper>> response = quizController.getQuestionsOfQuiz(quizId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("blue", response.getBody().get(1).getOption3());
        verify(quizService, times(1)).getQuestionsFromQuiz(quizId);
    }

    @Test
    void testGetResultOfQuiz() {
        // Arrange
        int quizId = 10;
        List<Responses> responses = Arrays.asList(
                new Responses(1,"pink"),
                new Responses(2, "B")
        );

        when(quizService.getResults(eq(quizId), anyList()))
                .thenReturn(ResponseEntity.ok(2)); // e.g., 2 correct answers

        // Act
        ResponseEntity<Integer> response = quizController.getResultOfQuiz(quizId, responses);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody());
        verify(quizService, times(1)).getResults(eq(quizId), anyList());
    }
}
