package com.flyingpig.service;

import com.flyingpig.dataobject.dto.QuestionAnalysis;
import com.flyingpig.dataobject.entity.ExamInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    List<QuestionAnalysis> listQuestionAnalysisById(Long examId);
}
