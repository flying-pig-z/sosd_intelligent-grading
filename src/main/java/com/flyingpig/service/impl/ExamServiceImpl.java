package com.flyingpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flyingpig.dataobject.dto.*;
import com.flyingpig.dataobject.entity.*;
import com.flyingpig.dataobject.entity.User;
import com.flyingpig.mapper.*;
import com.flyingpig.service.IExamService;
import com.flyingpig.util.DataAnalysisUtil;
import com.flyingpig.util.ExamUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements IExamService {

    @Autowired
    ExamInfoMapper examInfoMapper;
    @Autowired
    ExamMapper examMapper;
    @Autowired
    StudentMapper studentMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ExamUtil examUtil;

    @Autowired
    TeacherClassRelationMapper teacherClassRelationMapper;

    public Integer getExamNum(Long examInfoId) {
        return examMapper.selectCount(
                new LambdaQueryWrapper<Exam>().eq(Exam::getExamInfoId, examInfoId)
        );
    }

    public Integer getClassExamNum(Long examInfoId, Long classId) {
        return examMapper.selectCount(
                new LambdaQueryWrapper<Exam>().eq(Exam::getExamInfoId, examInfoId).eq(Exam::getClassId, classId)
        );
    }



    @Override
    public List<RankInfo> getRankByExamIdAndClassId(Long examInfoId, Long classId) {
        Long schoolExamNum = Long.parseLong(getExamNum(examInfoId).toString());
        Long classExamNum = Long.parseLong(getClassExamNum(examInfoId, classId).toString());

        LambdaQueryWrapper<Exam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Exam::getExamInfoId, examInfoId).eq(Exam::getClassId, classId).orderByDesc(Exam::getScore);
        List<Exam> studentExams = examMapper.selectList(queryWrapper);

        List<RankInfo> rankInfos = new ArrayList<>();
        for (Exam studentExam : studentExams) {
            User user = userMapper.selectById(studentExam.getStudentId());
            RankInfo rankInfo = new RankInfo(null, classExamNum, null, schoolExamNum, user.getName(), studentExam.getScore());
            rankInfos.add(rankInfo);
        }

        return rankInfos;
    }

    @Override
    public Rate getSchoolRateByExamInfoId(Long examId) {
        Double totalScore = examInfoMapper.selectById(examId).getTotalScore();
        Double passScore = totalScore * 0.6;
        Double excellentScore = totalScore * 0.8;

        LambdaQueryWrapper<Exam> queryWrapper = new LambdaQueryWrapper<>();
        int examCount = examMapper.selectCount(queryWrapper.eq(Exam::getExamInfoId, examId));
        int passCount = examMapper.selectCount(queryWrapper.ge(Exam::getScore, passScore));
        int noPassCount = examCount - passCount;
        queryWrapper.clear();
        int excellentCount = examMapper.selectCount(queryWrapper.eq(Exam::getExamInfoId, examId).ge(Exam::getScore, passScore));
        passCount = passCount - excellentCount;

        String noPassRate= DataAnalysisUtil.parseToPercentage(noPassCount,examCount);
        String passRate= DataAnalysisUtil.parseToPercentage(passCount,examCount);
        String excellenceRate= DataAnalysisUtil.parseToPercentage(excellentCount,examCount);

        return new Rate(noPassRate, passRate, excellenceRate);
    }

    @Override
    public Rate getClassRateByExamInfoIdAndClassId(Long examId, Long classId) {
        Double totalScore = examInfoMapper.selectById(examId).getTotalScore();
        Double passScore = totalScore * 0.6;
        Double excellentScore = totalScore * 0.8;
        LambdaQueryWrapper<Exam> queryWrapper = new LambdaQueryWrapper<>();
        int examCount = examMapper.selectCount(queryWrapper.eq(Exam::getExamInfoId, examId).eq(Exam::getClassId, classId));
        int passCount = examMapper.selectCount(queryWrapper.ge(Exam::getScore, passScore));
        int noPassCount = examCount - passCount;
        queryWrapper.clear();
        int excellentCount = examMapper.selectCount(queryWrapper.eq(Exam::getExamInfoId, examId).eq(Exam::getClassId, classId).ge(Exam::getScore, passScore));
        passCount = passCount - excellentCount;

        String noPassRate= DataAnalysisUtil.parseToPercentage(noPassCount,examCount);
        String passRate= DataAnalysisUtil.parseToPercentage(passCount,examCount);
        String excellenceRate= DataAnalysisUtil.parseToPercentage(excellentCount,examCount);

        return new Rate(noPassRate, passRate, excellenceRate);
    }

    @Override
    public ExamScore getSchoolScoreByExamInfoId(Long examId) {
        return examUtil.getSchoolExamScoreByExamId(examId);
    }

}
