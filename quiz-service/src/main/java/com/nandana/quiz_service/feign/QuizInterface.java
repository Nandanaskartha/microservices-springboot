package com.nandana.quiz_service.feign;

import com.nandana.quiz_service.model.QuestionWrapper;
import com.nandana.quiz_service.model.Responses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    @GetMapping("/question/generate")
    public ResponseEntity<List<Integer>> getQuestionsforQuiz(@RequestParam String category, @RequestParam int numQ);

    @PostMapping("/question/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@RequestBody List<Integer> questionIds);

    @PostMapping("/question/score")
    public ResponseEntity<Integer> calculateScore(@RequestBody List<Responses> responses);

}
