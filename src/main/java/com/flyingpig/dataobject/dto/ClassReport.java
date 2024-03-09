package com.flyingpig.dataobject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClassReport {
    private Long examInfoId;

    private Long classId;

    private String name;
    private String grade;
    private String subject;
    private Double perScoreOfClass;
}
