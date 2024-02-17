package com.flyingpig.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("exam-question")
public class ExamQuestion {
    Long id;

    String content;

    String referenceAnswer;

    Long type;

    Long examId;

    Double totalScore;

}
