package com.flyingpig.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.flyingpig.dataobject.vo.CreateExam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2024-03-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("exam_info")
@ApiModel(value="ExamInfo对象", description="")
public class ExamInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private String type;

    private String grade;

    @ApiModelProperty(value = "学科")
    private String subject;

    private Double totalScore;

    private Long studentNum;

    private Long num;

    private String approxTime;

    private LocalDateTime detailTime;



    public ExamInfo(CreateExam createExam){
        name=createExam.getName();
        type=createExam.getType();
        grade=createExam.getGrade();
        subject=createExam.getSubject();
        totalScore=createExam.getTotalScore();
        approxTime=createExam.getApproxTime();
        detailTime=createExam.getDetailTime();
        num=0L;
    }

}
