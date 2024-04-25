package com.flyingpig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.flyingpig.dataobject.dto.QuestionTask;
import com.flyingpig.dataobject.dto.StudentExamInfo;
import com.flyingpig.dataobject.entity.ExamInfo;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
public interface IExamInfoService extends IService<ExamInfo> {

    List<ExamInfo> listExamInfoByKeyword(String time, String type, String subject, String grade);

    List<QuestionTask> listQuestionTaskById(Long id);


    Set<StudentExamInfo> listExamInfoByStudentId(Long id);
}
