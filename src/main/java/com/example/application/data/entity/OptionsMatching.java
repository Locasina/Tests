package com.example.application.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class OptionsMatching {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer questionId;
    private Integer columnId;
    private Integer optionId;
    private String text;
    @ManyToOne
    private Question question;


}
