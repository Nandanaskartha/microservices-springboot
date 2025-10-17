package com.nandana.quiz_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionid;
    private String category;
    private String difficultylevel;
    private String questiontitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String rightanswer;

}
