package com.flyingpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.flyingpig.dataobject.dto.AnswerSituation;
import com.flyingpig.dataobject.dto.QuestionScoreInfo;
import com.flyingpig.dataobject.entity.Question;
import com.flyingpig.dataobject.entity.QuestionInfo;
import com.flyingpig.dataobject.vo.ScoreQuestion;
import com.flyingpig.mapper.QuestionInfoMapper;
import com.flyingpig.mapper.QuestionMapper;
import com.flyingpig.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flyingpig.util.QuestionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    @Autowired
    QuestionInfoMapper questionInfoMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionUtil questionUtil;

    @Override
    public QuestionScoreInfo getQuestionScoreInfoByQuestionInfoId(Long questionInfoId) {
        //获取题目总分和是第几题
        QuestionInfo questionInfo = questionInfoMapper.selectById(questionInfoId);
        QuestionScoreInfo result=new QuestionScoreInfo();
        result.setTotalScore(questionInfo.getTotalScore());

        //获取题目其他信息
        LambdaQueryWrapper<Question> questionLambdaQueryWrapper=new LambdaQueryWrapper<>();
        questionLambdaQueryWrapper.eq(Question::getQuestionInfoId,questionInfoId);
        Question question=questionMapper.selectList(questionLambdaQueryWrapper).get(0);
        result.setQuestionId(question.getId());
        result.setImage(question.getAnswer());
        result.setOrder(questionUtil.getUncorrectedAndCorrectedQuestionNum(questionInfoId).getCorrectedNum()+1);
        return result;
    }

    @Override
    public void updateScoreAndCommentByScoreQuestion(ScoreQuestion scoreQuestion) {
        Question question=new Question();
        BeanUtils.copyProperties(scoreQuestion,question);
        this.updateById(question);
    }

    @Override
    public List<AnswerSituation> getAnswerSituationByStudentIdAndQuestionInfoId(Long studentId, Long examInfoId) {
        LambdaQueryWrapper<QuestionInfo> questionInfoLambdaQueryWrapper=Wrappers.lambdaQuery(QuestionInfo.class)
                .eq(QuestionInfo::getExamInfoId,examInfoId).orderByAsc(QuestionInfo::getPosition);
        List<QuestionInfo> questionInfos= questionInfoMapper.selectList(questionInfoLambdaQueryWrapper);
        List<AnswerSituation> result= new ArrayList<>();
        for(QuestionInfo questionInfo:questionInfos){
            AnswerSituation answerSituation=new AnswerSituation();
            answerSituation.setQuestionNo(questionInfo.getPosition());
            answerSituation.setTotalScore(questionInfo.getTotalScore());
            LambdaQueryWrapper<Question> questionLambdaQueryWrapper= Wrappers.lambdaQuery(Question.class)
                    .eq(Question::getQuestionInfoId,questionInfo.getId()).eq(Question::getStudentId,studentId);
            Question question=questionMapper.selectOne(questionLambdaQueryWrapper);
            if(question!=null){
                answerSituation.setRealScore(question.getScore());
                Double classPerScore=questionUtil.getClassPerScore(question.getId(), question.getClassId());
                answerSituation.setClassPerScore(classPerScore);
                result.add(answerSituation);
            }


        }
        return result;
    }
}
