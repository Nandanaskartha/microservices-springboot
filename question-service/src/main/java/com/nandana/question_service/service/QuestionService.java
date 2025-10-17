package com.nandana.question_service.service;


import com.nandana.question_service.dao.QuestionDao;
import com.nandana.question_service.model.Question;
import com.nandana.question_service.model.QuestionWrapper;
import com.nandana.question_service.model.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String cat) {
        try{
            return new ResponseEntity<>(questionDao.findAllByCategory(cat), HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Integer>> getQuestionsforQuiz(String category, int numQ) {
        List<Integer> questionIds = questionDao.findRandomQuestionsByCategory(numQ, category);

        return new ResponseEntity<>(questionIds, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestions(List<Integer> questionIds) {
        List<QuestionWrapper> qws = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for(int qid:questionIds){
            questions.add(questionDao.findById(qid).get());
        }
        for(Question question:questions){
            QuestionWrapper qw = new QuestionWrapper(question.getQuestionid(),question.getQuestiontitle(),question.getOption1(),question.getOption2(),question.getOption3(),question.getOption4() );
            qws.add(qw);
        }
        return new ResponseEntity<>(qws, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScoreFromResponses(List<Responses> responses) {
        Integer right=0;
        for(Responses r:responses){
            Question question = questionDao.findById(r.getId()).get();
            if(r.getAnswer().equals(question.getRightanswer()))
                right++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
