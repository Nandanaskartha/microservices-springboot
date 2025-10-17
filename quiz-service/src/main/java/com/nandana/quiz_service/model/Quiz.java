package com.nandana.quiz_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int quizid;
    private String quiztitle;

    @ElementCollection
    private List<Integer> questions;

}
