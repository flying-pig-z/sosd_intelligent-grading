package com.flyingpig.dataobject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankInfo {
    private Long classRank;
    private Long classPersonNum;
    private Long gradeRank;
    private Long gradePersonNum;
    private String name;
    private Double realScore;
}
