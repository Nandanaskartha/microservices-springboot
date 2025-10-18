package com.nandana.question_service.controller;

import com.nandana.question_service.model.Question;
import com.nandana.question_service.model.QuestionWrapper;
import com.nandana.question_service.model.Responses;
import com.nandana.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{cat}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String cat){
        return questionService.getQuestionsByCategory(cat);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable int id){
        return questionService.deleteQuestionById(id);
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsforQuiz(@RequestParam String category, @RequestParam int numQ){
        return questionService.getQuestionsforQuiz(category, numQ);
    }
    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@RequestBody List<Integer> questionIds){
//        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestions(questionIds);
    }

    @PostMapping("/score")
    public ResponseEntity<Integer> calculateScore(@RequestBody List<Responses> responses){
        return questionService.getScoreFromResponses(responses);
    }
}
