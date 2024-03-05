package com.flyingpig.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.SchoolExamScore;
import com.flyingpig.dataobject.entity.ExamInfo;
import com.flyingpig.dataobject.entity.Exam;
import com.flyingpig.mapper.ExamInfoMapper;
import com.flyingpig.mapper.ExamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ExamUtil {
    @Autowired
    ExamMapper studentExamMapper;
    @Autowired
    ExamInfoMapper examMapper;

    public SchoolExamScore getSchoolExamScoreByExamId(Long examId) {

        double totalScore = examMapper.selectById(examId).getTotalScore();

        LambdaQueryWrapper<Exam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Exam::getExamInfoId, examId).orderByDesc(Exam::getRealScore);
        List<Exam> studentExams = studentExamMapper.selectList(queryWrapper);
        double highestScore = 0;
        if (studentExams.size() >= 1) {
            highestScore = studentExams.get(0).getRealScore();
        }

        double scoreSum = 0;
        for (Exam studentExam : studentExams) {
            scoreSum = scoreSum + studentExam.getRealScore();
        }
        double perScore = scoreSum / studentExams.size();

        return new SchoolExamScore(highestScore, perScore, totalScore);
    }

    public String getCompleteRateByExamId(Long examId) {
        LambdaQueryWrapper<Exam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Exam::getExamInfoId, examId);
        int total = studentExamMapper.selectCount(queryWrapper);
        ExamInfo exam = examMapper.selectById(examId);
        Long questionGroupNum = exam.getQuestionGroupNum();
        int complete = studentExamMapper.selectCount(queryWrapper.select(Exam::getCorrectedQuestionGroupNum));
        return DataAnalysisUtil.parseToPercentage(complete, total*questionGroupNum);
    }
}
