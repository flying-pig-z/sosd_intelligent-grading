package com.flyingpig.service;

import com.flyingpig.dataobject.dto.ExamTask;
import com.flyingpig.dataobject.dto.QuestionTask;
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

    List<ExamTask> listExamTaskByTeacherId(Long id);

    List<QuestionTask> listQuestionTaskByTeacherId(Long id);
}
