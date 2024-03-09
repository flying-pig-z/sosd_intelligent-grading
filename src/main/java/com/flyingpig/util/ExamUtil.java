package com.flyingpig.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.ExamScore;
import com.flyingpig.dataobject.entity.Exam;
import com.flyingpig.mapper.ExamInfoMapper;
import com.flyingpig.mapper.ExamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ExamUtil {
    @Autowired
    ExamMapper examMapper;
    @Autowired
    ExamInfoMapper examInfoMapper;

    public ExamScore getSchoolExamScoreByExamId(Long examInfoId) {

        double totalScore = examInfoMapper.selectById(examInfoId).getTotalScore();

        LambdaQueryWrapper<Exam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Exam::getExamInfoId, examInfoId).orderByDesc(Exam::getScore);
        List<Exam> exams = examMapper.selectList(queryWrapper);
        double highestScore = 0;
        if (exams.size() >= 1) {
            highestScore = exams.get(0).getScore();
        }

        double scoreSum = 0;
        for (Exam studentExam : exams) {
            scoreSum = scoreSum + studentExam.getScore();
        }
        double perScore = scoreSum / exams.size();

        return new ExamScore(highestScore, perScore, totalScore);
    }

    public ExamScore getClassExamScoreByExamIdAndClassId(Long examInfoId, Long classId) {

        double totalScore = examInfoMapper.selectById(examInfoId).getTotalScore();

        LambdaQueryWrapper<Exam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Exam::getExamInfoId, examInfoId).eq(Exam::getClassId,classId).orderByDesc(Exam::getScore);
        List<Exam> studentExams = examMapper.selectList(queryWrapper);
        double highestScore = 0;
        if (studentExams.size() >= 1) {
            highestScore = studentExams.get(0).getScore();
        }

        double scoreSum = 0;
        for (Exam studentExam : studentExams) {
            scoreSum = scoreSum + studentExam.getScore();
        }
        double perScore = scoreSum / studentExams.size();

        return new ExamScore(highestScore, perScore, totalScore);
    }

//    public String getCompleteRateByExamId(Long examId) {
//        LambdaQueryWrapper<Exam> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Exam::getExamInfoId, examId);
//        int total = studentExamMapper.selectCount(queryWrapper);
//        ExamInfo examInfo = examMapper.selectById(examId);
//        Long questionGroupNum = examInfo.getQuestionGroupNum();
//        int complete = studentExamMapper.selectCount(queryWrapper.select(Exam::getCorrectedQuestionGroupNum));
//        return DataAnalysisUtil.parseToPercentage(complete, total*questionGroupNum);
//    }
}
