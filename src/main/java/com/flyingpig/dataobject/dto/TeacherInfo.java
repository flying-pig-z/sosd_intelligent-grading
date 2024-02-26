package com.flyingpig.dataobject.dto;

import com.flyingpig.dataobject.entity.SchoolClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherInfo {
    Long id;
    String name;
    String teachingSubject;
    List<SchoolClass> schoolClassList;

}
