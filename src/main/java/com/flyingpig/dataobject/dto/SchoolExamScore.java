package com.flyingpig.dataobject.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolExamScore {
    private Double highestScore;
    private Double perScore;
    private Double totalScore;
}
