package com.flyingpig.dataobject.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestion {
    private String content;
    private String type;
    private Double totalScore;
    private String referenceAnswer;
    private Long examInfoId;
    private Long position;
}
