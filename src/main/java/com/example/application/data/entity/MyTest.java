package com.example.application.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class MyTest {
    @Id
    private Integer Id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private User user;
    private Integer state;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id")
    private Test test;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalTime testTimer;

}

