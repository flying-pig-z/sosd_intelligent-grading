package com.flyingpig.dataobject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionScoreInfo {
    private Long questionId;

    private Double totalScore;

    private Long order;

    private String image;

}
