package com.flyingpig.dataobject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerSituation {
    private Long questionNo;
    private Double totalScore;
    private Double realScore;
    private Double classPerScore;
}
