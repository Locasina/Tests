package com.example.application.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
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
