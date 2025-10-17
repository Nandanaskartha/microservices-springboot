package com.nandana.quiz_service.service;

import com.nandana.quiz_service.dao.QuizDao;
import com.nandana.quiz_service.feign.QuizInterface;
import com.nandana.quiz_service.model.Question;
import com.nandana.quiz_service.model.QuestionWrapper;
import com.nandana.quiz_service.model.Quiz;
import com.nandana.quiz_service.model.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;


    public ResponseEntity<String> createQuiz(String cat, int numQ, String title) {
        List<Integer> questions = quizInterface.getQuestionsforQuiz(cat,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setQuiztitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromQuiz(int id) {
         Optional<Quiz> quiz = quizDao.findById(id);
         if(quiz.isPresent()){
             List<Integer> questions = quiz.get().getQuestions();
             List<QuestionWrapper> qwr = quizInterface.getQuestions(questions).getBody();
             return new ResponseEntity<>(qwr, HttpStatus.OK);
         }
         else
             return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Integer> getResults(int id, List<Responses> responses) {
        return quizInterface.calculateScore(responses);
    }
}
