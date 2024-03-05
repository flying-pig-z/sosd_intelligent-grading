package com.flyingpig.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.entity.Question;
import com.flyingpig.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class QuestionUtil {

    @Autowired
    QuestionMapper studentQuestionMapper;

    public Double getSchoolPerScore(Long questionId) {
        LambdaQueryWrapper<Question> studentQuestionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentQuestionLambdaQueryWrapper.eq(Question::getQuestionInfoId, questionId);
        Integer personCount = studentQuestionMapper.selectCount(studentQuestionLambdaQueryWrapper);
        Double scoreSum = studentQuestionMapper.selectList(studentQuestionLambdaQueryWrapper)
                .stream()
                .mapToDouble(Question::getScore)
                .sum();
        System.out.println(personCount);
        return scoreSum / personCount;
    }

    public Double getClassPerScore(Long questionId, Long classId) {
        LambdaQueryWrapper<Question> studentQuestionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentQuestionLambdaQueryWrapper.eq(Question::getQuestionInfoId, questionId).eq(Question::getClassId, classId);
        Integer personCount = studentQuestionMapper.selectCount(studentQuestionLambdaQueryWrapper);
        List<Question> questionList = studentQuestionMapper.selectList(studentQuestionLambdaQueryWrapper);
        Double scoreSum = questionList.stream().mapToDouble(Question::getScore).sum();
        System.out.println(personCount);
        return scoreSum / personCount;
    }

}
