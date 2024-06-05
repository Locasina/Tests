package com.example.application.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Answer {
    @Id
    private Integer Id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private Question question;
    private String text;
    public String toString(){
        return text;
    }
}
