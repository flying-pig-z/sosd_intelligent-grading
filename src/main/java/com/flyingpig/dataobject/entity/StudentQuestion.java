package com.flyingpig.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("student_question")
public class StudentQuestion {
    Long id;
    Long studentId;
    Long examQuestionId;
    String questionImage;
    Double score;
    Boolean gradeOrNot;

}
