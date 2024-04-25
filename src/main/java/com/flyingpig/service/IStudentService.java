package com.flyingpig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.flyingpig.dataobject.dto.ClassStudent;
import com.flyingpig.dataobject.entity.Student;
import com.flyingpig.dataobject.dto.StudentInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
public interface IStudentService extends IService<Student> {

    List<ClassStudent> getClassStudentByClassId(Long classId);

    StudentInfo getStudentInfoById(Long id);
}
