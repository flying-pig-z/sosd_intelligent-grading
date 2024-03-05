package com.flyingpig.dataobject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamTask {
    private Long id;

    private String examName;

    private String grade;

    private String subject;
    private String schedule;
}
