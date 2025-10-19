package com.nandana.question_service.service;

import com.nandana.question_service.dao.QuestionDao;
import com.nandana.question_service.model.Question;
import com.nandana.question_service.model.QuestionWrapper;
import com.nandana.question_service.model.Responses;
import org.apache.hc.core5.http.impl.Http1StreamListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceTest {

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private QuestionService questionService;

    private Question q1;
    private Question q2;
    private QuestionWrapper qw1;
    private QuestionWrapper qw2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        q1 = new Question();
        q1.setQuestionid(1);
        q1.setCategory("Math");
        q1.setDifficultylevel("Easy");
        q1.setQuestiontitle("What is 2+2?");
        q1.setOption1("2");
        q1.setOption2("3");
        q1.setOption3("4");
        q1.setOption4("1");
        q1.setRightanswer("4");

        q2 = new Question();
        q2.setQuestionid(2);
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
        List<Question> allQuestions = Arrays.asList(q1, q2);
        when(questionDao.findAll()).thenReturn(allQuestions);
        ResponseEntity<List<Question>> response = questionService.getAllQuestions();
        assertEquals(allQuestions, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(questionDao, times(1)).findAll();
    }

    @Test
    void getQuestionsByCategory() {
        List<Question> mathQuestions = Arrays.asList(q1, q2);
        when(questionDao.findAllByCategory("Math")).thenReturn(mathQuestions);

        ResponseEntity<List<Question>> response = questionService.getQuestionsByCategory("Math");

        assertEquals(mathQuestions, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(questionDao, times(1)).findAllByCategory("Math");
    }

    @Test
    void addQuestion() {
        when(questionDao.save(q1)).thenReturn(q1);

        ResponseEntity<String> response = questionService.addQuestion(q1);

        assertEquals("Success", response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(questionDao, times(1)).save(q1);
    }

    @Test
    void getQuestionsforQuiz() {
        List<Integer> ids = Arrays.asList(1, 2);
        when(questionDao.findRandomQuestionsByCategory( 2, "Math")).thenReturn(ids);

        ResponseEntity<List<Integer>> response = questionService.getQuestionsforQuiz("Math", 2);

        assertEquals(ids, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(questionDao, times(1)).findRandomQuestionsByCategory(2, "Math");
    }

    @Test
    void getQuestions() {
        List<Integer> ids = Arrays.asList(1, 2);
        when(questionDao.findById(1)).thenReturn(Optional.of(q1));
        when(questionDao.findById(2)).thenReturn(Optional.of(q2));
        ResponseEntity<List<QuestionWrapper>> response = questionService.getQuestions(ids);

        assertEquals(2, response.getBody().size());
        assertEquals(q1.getQuestiontitle(), response.getBody().get(0).getQuestiontitle());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(questionDao, times(1)).findById(1);
        verify(questionDao, times(1)).findById(2);
    }

    @Test
    void getScoreFromResponses() {
        Responses r1 = new Responses(1, "4");  // correct
        Responses r2 = new Responses(2, "3");  // wrong
        System.out.println("q1 ID: " + q1.getQuestionid());
        System.out.println("q2 ID: " + q2.getQuestionid());
        List<Responses> responses = Arrays.asList(r1, r2);

        when(questionDao.findById(1)).thenReturn(Optional.of(q1));
        when(questionDao.findById(2)).thenReturn(Optional.of(q2));

        ResponseEntity<Integer> result = questionService.getScoreFromResponses(responses);

        assertEquals(Integer.valueOf(1), result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(questionDao, times(1)).findById(1);
        verify(questionDao, times(1)).findById(2);
    }

    @Test
    void deleteQuestionById() {
        int questionId=1;
        when(questionDao.findById(questionId)).thenReturn(Optional.of(q1));
        doNothing().when(questionDao).deleteById(questionId);
        ResponseEntity<String>response = questionService.deleteQuestionById(questionId);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Deleted question with id: 1"));
        assertTrue(response.getBody().contains("from Category: Math"));

        verify(questionDao, times(1)).deleteById(questionId);
        verify(questionDao, times(1)).findById(questionId);
    }

    @Test
    void updateQuestionById() {
        int questionId=1;
        when(questionDao.findById(1)).thenReturn(Optional.of(q1));
        when(questionDao.save(q1)).thenReturn(q1);
        //Act
        ResponseEntity<String> response= questionService.updateQuestionById(questionId, q1);
        //Assert
        assertEquals("Success", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(questionDao, times(1)).findById(questionId);
        verify(questionDao, times(1)).save(q1);
    }
}