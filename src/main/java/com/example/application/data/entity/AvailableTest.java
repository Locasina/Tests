package com.example.application.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class AvailableTest {
    @Id
    private Integer Id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private User user;
    private Integer state;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id")
    private Test test;

}
