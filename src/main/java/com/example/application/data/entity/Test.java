package com.example.application.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class Test {
    @Id
    private Integer Id;
    private String text;
    private String title;
    private String subtitle;
}

