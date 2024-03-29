package com.flyingpig.dataobject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamTask {
    private Long id;

    private String name;

    private String grade;

    private String subject;
}
