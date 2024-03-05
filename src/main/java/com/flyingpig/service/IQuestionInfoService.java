package com.flyingpig.service;

import com.flyingpig.dataobject.dto.QuestionAnalysis;
import com.flyingpig.dataobject.entity.QuestionInfo;
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
public interface IQuestionInfoService extends IService<QuestionInfo> {

    List<QuestionAnalysis> listQuestionAnalysisById(Long examId, Long classId);
}
