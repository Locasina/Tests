package com.example.application.data.entity;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class NewCreateTestData {
    @Id
    private Integer Id;
    private String text;
    private String name;
    private boolean correct;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "answer_id")
    private Answer answer;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comparisonAnswer_id")
    private ComparisonAnswer comparisonAnswer;

}
