package com.nandana.quiz_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class QuestionWrapper {

    private Integer questionid;
    private String questiontitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
}
