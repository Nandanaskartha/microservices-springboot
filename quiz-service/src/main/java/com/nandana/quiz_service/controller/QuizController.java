package com.nandana.quiz_service.controller;

import com.nandana.quiz_service.model.QuizDto;
import com.nandana.quiz_service.model.QuestionWrapper;
import com.nandana.quiz_service.model.Responses;
import com.nandana.quiz_service.service.QuizService;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        return quizService.createQuiz(quizDto.getCategory(), quizDto.getNumQuestions(), quizDto.getQuizTitle());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsOfQuiz(@PathVariable int id){
        return quizService.getQuestionsFromQuiz(id);
    }

    @PostMapping("/quizresult/{id}")
    public ResponseEntity<Integer> getResultOfQuiz(@PathVariable int id, @RequestBody List<Responses> responses){
        return quizService.getResults(id, responses);
    }

}
