package com.flyingpig.service;

import com.flyingpig.dataobject.dto.TeacherQuestionGroup;
import com.flyingpig.dataobject.dto.TotalAndCorrectedAmount;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-02-22
 */
public interface IQuestionGroupService  {

    List<TeacherQuestionGroup> getTeacherQuestionGroupByExamId(Long examId);

    TotalAndCorrectedAmount getTotalAndRemainingAmountById(Long id);
}
