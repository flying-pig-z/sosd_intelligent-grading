package com.flyingpig.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.QuestionCorrectedAndUnCorrectedNum;
import com.flyingpig.dataobject.entity.Question;
import com.flyingpig.dataobject.entity.QuestionInfo;
import com.flyingpig.mapper.ExamInfoMapper;
import com.flyingpig.mapper.QuestionInfoMapper;
import com.flyingpig.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class QuestionUtil {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionInfoMapper questionInfoMapper;

    @Autowired
    ExamInfoMapper examInfoMapper;

    public Double getSchoolPerScore(Long questionId) {
        LambdaQueryWrapper<Question> studentQuestionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentQuestionLambdaQueryWrapper.eq(Question::getQuestionInfoId, questionId);
        Integer personCount = questionMapper.selectCount(studentQuestionLambdaQueryWrapper);
        Double scoreSum = questionMapper.selectList(studentQuestionLambdaQueryWrapper)
                .stream()
                .mapToDouble(Question::getScore)
                .sum();
        System.out.println(personCount);
        return scoreSum / personCount;
    }

    public Double getClassPerScore(Long questionId, Long classId) {
        LambdaQueryWrapper<Question> studentQuestionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentQuestionLambdaQueryWrapper.eq(Question::getQuestionInfoId, questionId).eq(Question::getClassId, classId);
        Integer personCount = questionMapper.selectCount(studentQuestionLambdaQueryWrapper);
        List<Question> questionList = questionMapper.selectList(studentQuestionLambdaQueryWrapper);
        Double scoreSum = questionList.stream().mapToDouble(Question::getScore).sum();
        System.out.println(personCount);
        return scoreSum / personCount;
    }

    public QuestionCorrectedAndUnCorrectedNum getUncorrectedAndCorrectedQuestionNum(Long questionInfoId){
        //先求题目总数，即试卷总数
        QuestionInfo questionInfo=questionInfoMapper.selectById(questionInfoId);
        Long totalNum=examInfoMapper.selectById(questionInfo.getExamInfoId()).getNum();
        //再求改了多少试卷
        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        questionLambdaQueryWrapper.eq(Question::getQuestionInfoId, questionInfoId);
        Long correctedNum=questionMapper.findCorrectedNum();
        QuestionCorrectedAndUnCorrectedNum questionCorrectedAndUnCorrectedNum=
                new QuestionCorrectedAndUnCorrectedNum(correctedNum,totalNum-correctedNum);
        return questionCorrectedAndUnCorrectedNum;
    }




}
