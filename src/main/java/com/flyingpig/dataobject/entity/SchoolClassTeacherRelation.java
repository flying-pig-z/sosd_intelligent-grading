package com.flyingpig.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("")
public class SchoolClassTeacherRelation {
    Long id;
    Long schoolClassId;
    Long teacherId;
}
