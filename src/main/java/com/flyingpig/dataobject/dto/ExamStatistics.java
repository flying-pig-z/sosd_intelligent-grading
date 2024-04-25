package com.flyingpig.dataobject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamStatistics {
    private Double schoolHighestScore;
    private Double schoolPerScore;
    private Double schoolTotalScore;
    private Double classHighestScore;
    private Double classPerScore;
    private Double classTotalScore;
}
