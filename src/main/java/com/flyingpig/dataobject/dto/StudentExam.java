package com.flyingpig.dataobject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentExam {
    public Long examId;
    public String examName;
    public String examGrade;
    public String subject;

    public LocalDateTime examTime;

    public Double score;
}
