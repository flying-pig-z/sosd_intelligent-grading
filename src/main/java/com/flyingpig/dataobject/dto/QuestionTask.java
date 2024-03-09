package com.flyingpig.dataobject.dto;

import com.flyingpig.dataobject.entity.ExamInfo;
import com.flyingpig.dataobject.entity.QuestionInfo;
import com.flyingpig.util.DataAnalysisUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionTask {
    Long questionInfoId;
    String name;
    Long position;
    String grade;
    String subject;
    String type;

    Long uncorrectedNum;

    String completeRate;


    public QuestionTask(ExamInfo examInfo, QuestionInfo questionInfo
            ,QuestionCorrectedAndUnCorrectedNum questionCorrectedAndUnCorrectedNum){
        questionInfoId=questionInfo.getId();
        name=examInfo.getName();
        position =questionInfo.getPosition();
        grade=examInfo.getGrade();
        type=questionInfo.getType();
        subject=examInfo.getSubject();
        uncorrectedNum= questionCorrectedAndUnCorrectedNum.getUnCorrectedNum();
        Long correctedNum=questionCorrectedAndUnCorrectedNum.getCorrectedNum();
        completeRate= DataAnalysisUtil.parseToPercentage(correctedNum,correctedNum+uncorrectedNum);

    }
}
