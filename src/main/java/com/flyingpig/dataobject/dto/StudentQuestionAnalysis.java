package com.flyingpig.dataobject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentQuestionAnalysis {
    private String content;
    private String referenceAnswer;
    private Double schoolPerScore;
    private String studentAnswer;
    private Double studentScore;
}
