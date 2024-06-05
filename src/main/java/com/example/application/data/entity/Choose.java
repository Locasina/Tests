package com.example.application.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Choose {
    @Id
    private Integer Id;
    @ManyToOne(fetch = FetchType.EAGER)
    User user;
    @ManyToOne(fetch = FetchType.EAGER)
    Answer answer;

}
