package com.example.application.util;

import com.example.application.data.entity.Answer;
import com.example.application.data.entity.ComparisonAnswer;
import com.example.application.data.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestData {
    List<Question> questions;
    List<List<Answer>> answers;
    Map<Integer, String> chosenOptions;
    List<List<ComparisonAnswer>> compAnswers;
}
