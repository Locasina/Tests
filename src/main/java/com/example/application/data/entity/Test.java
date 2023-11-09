package com.example.application.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NonNull
@RequiredArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer testId;
    private String text;
}
