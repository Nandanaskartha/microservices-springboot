package com.nandana.question_service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Responses {
    private int id;
    private String answer;

    public Responses(int i, String ans) {
        this.id=i;
        this.answer=ans;
    }
}
