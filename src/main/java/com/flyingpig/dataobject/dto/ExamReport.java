package com.flyingpig.dataobject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamReport {

    private Double classPassRate;

    private Double schoolPassRate;

    private Double classExcellentRate;

    private Double schoolExcellentRate;

    private Double classHighestScore;

    private Double classPerScore;

    private Long classHighestRank;

    private Long classPerRank;

}
