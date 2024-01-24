package com.example.application.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ComparisonAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private Question question;


    private String column1;
    private String column2;

}
