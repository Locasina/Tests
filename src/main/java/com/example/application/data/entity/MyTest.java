package com.example.application.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MyTest {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private User user;
    private Integer state;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id")
    private Test test;

}
