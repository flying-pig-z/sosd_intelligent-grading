package com.flyingpig.dataobject.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreQuestion {
    private Long id;
    private Long score;
    private String comment;
    private Long scoreOrder;


}
