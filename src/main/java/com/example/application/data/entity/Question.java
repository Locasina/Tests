package com.example.application.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    private String text;
    private Integer testId;
    private Integer typeQ;


}
