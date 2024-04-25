package com.flyingpig.dataobject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassRank {
    private Long classRank;
    private Long schoolRank;
    private String studentName;
    private Double score;
}
