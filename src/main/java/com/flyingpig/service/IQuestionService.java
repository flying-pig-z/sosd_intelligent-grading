package com.flyingpig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.flyingpig.dataobject.dto.AnswerSituation;
import com.flyingpig.dataobject.dto.QuestionScoreInfo;
import com.flyingpig.dataobject.entity.Question;
import com.flyingpig.dataobject.vo.ScoreQuestion;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
public interface IQuestionService extends IService<Question> {

    QuestionScoreInfo getQuestionScoreInfoByQuestionInfoId(Long questionInfoId);

    void updateScoreAndCommentByScoreQuestion(ScoreQuestion scoreQuestion);

    List<AnswerSituation> getAnswerSituationByStudentIdAndQuestionInfoId(Long studentId, Long questionInfoId);
}
