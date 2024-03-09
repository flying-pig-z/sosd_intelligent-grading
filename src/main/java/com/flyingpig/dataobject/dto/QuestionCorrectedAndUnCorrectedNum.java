package com.flyingpig.dataobject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCorrectedAndUnCorrectedNum {
    private Long correctedNum;

    private Long unCorrectedNum;
}
