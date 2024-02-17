package com.flyingpig.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("student_exam")
public class StudentExam {
    Long id;
    Long examId;
    Long studentId;
    String studentExamImage;
}
