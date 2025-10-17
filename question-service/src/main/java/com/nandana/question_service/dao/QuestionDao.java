package com.nandana.question_service.dao;


import com.nandana.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findAllByCategory(String cat);


    @Query(value = "SELECT questionid from Question q where q.category=:cat ORDER BY RANDOM() LIMIT :numQ",nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(int numQ, String cat);
}
