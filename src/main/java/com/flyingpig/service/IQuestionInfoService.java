package com.flyingpig.service;

import com.flyingpig.dataobject.dto.QuestionAnalysis;
import com.baomidou.mybatisplus.extension.service.IService;
import com.flyingpig.dataobject.dto.StudentQuestionAnalysis;
import com.flyingpig.dataobject.entity.QuestionInfo;
import com.flyingpig.dataobject.vo.CreateQuestion;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
public interface IQuestionInfoService extends IService<QuestionInfo> {

    List<QuestionAnalysis> listQuestionAnalysisById(Long examId, Long classId);

    List<StudentQuestionAnalysis> listStudentAnalysisByExamInfoIdAndStudentId(Long examInfoId, Long studentId);

    void addQuestionInfo(Long examInfoId, List<CreateQuestion> createQuestionList);
}
