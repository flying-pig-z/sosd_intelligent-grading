package com.flyingpig.dataobject.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentExamVO {

    private Long examInfoId;

    private Long studentNO;

    private Long classId;

    private String image;

    private Double score;

    private String comment;

}
