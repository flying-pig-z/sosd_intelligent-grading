package com.flyingpig.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
@TableName("question")
@ApiModel(value="Question对象", description="")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "得分")
    private Double score;

    @ApiModelProperty(value = "评分顺序")
    private Long scoreOrder;

    @ApiModelProperty(value = "学生id")
    private Long studentId;

    @ApiModelProperty(value = "班级id")
    private Long classId;

    @ApiModelProperty(value = "题目信息id")
    private Long questionInfoId;

    private String answer;

    private String comment;

}
