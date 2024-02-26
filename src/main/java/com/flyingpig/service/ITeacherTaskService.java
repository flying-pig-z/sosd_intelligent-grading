package com.flyingpig.service;

import com.flyingpig.dataobject.dto.TeacherQuestionGroup;
import com.flyingpig.dataobject.entity.Exam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.flyingpig.dataobject.entity.TeacherTask;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
public interface ITeacherTaskService extends IService<TeacherTask> {

    List<Exam> getTeacherExamTaskById(Long id);

    List<TeacherQuestionGroup> getTeacherQuestionGroupByTeacherId(Long id);
}
