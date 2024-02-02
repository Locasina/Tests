package com.example.application.data.entity;

import com.example.application.data.repository.AnswerRepository;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    private String text;

    private Integer typeQ;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id")
    private Test test;
    @OneToMany(fetch = FetchType.EAGER, targetEntity = Answer.class, cascade = CascadeType.ALL)
    @Column(name = "question_id")
    private List<Answer> answers;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = ComparisonAnswer.class, cascade = CascadeType.ALL)
    @Column(name = "question_id")
    private List<ComparisonAnswer> comparisonAnswers;




}
