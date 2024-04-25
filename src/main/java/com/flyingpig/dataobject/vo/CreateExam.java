package com.flyingpig.dataobject.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExam {
    private String name;
    private String type;
    private String grade;
    private String subject;
    private Double totalScore;
    private String approxTime;
    private LocalDateTime detailTime;
}
