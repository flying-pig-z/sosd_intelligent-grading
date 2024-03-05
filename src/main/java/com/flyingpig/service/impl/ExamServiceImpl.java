package com.flyingpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.ExamReport;
import com.flyingpig.dataobject.dto.Rate;
import com.flyingpig.dataobject.dto.RankInfo;
import com.flyingpig.dataobject.dto.SchoolExamScore;
import com.flyingpig.dataobject.entity.Exam;
import com.flyingpig.dataobject.entity.User;
import com.flyingpig.mapper.ExamInfoMapper;
import com.flyingpig.mapper.ExamMapper;
import com.flyingpig.mapper.StudentMapper;
import com.flyingpig.mapper.UserMapper;
import com.flyingpig.service.IExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flyingpig.util.DataAnalysisUtil;
import com.flyingpig.util.ExamUtil;
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
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements IExamService {

    @Autowired
    ExamInfoMapper examInfoMapper;
    @Autowired
    ExamMapper examMapper;
    @Autowired
    StudentMapper studentMapper;

    @Autowired
    UserMapper userMapper;

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
    public ExamReport getExamReportByExamId(Long examInfoId, Long classId) {
        return null;
    }

    @Override
    public List<RankInfo> getRankByExamIdAndClassId(Long examInfoId, Long classId) {
        Long schoolExamNum = Long.parseLong(getExamNum(examInfoId).toString());
        Long classExamNum = Long.parseLong(getClassExamNum(examInfoId, classId).toString());

        LambdaQueryWrapper<Exam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Exam::getExamInfoId, examInfoId).eq(Exam::getClassId, classId).orderByDesc(Exam::getRealScore);
        List<Exam> studentExams = examMapper.selectList(queryWrapper);

        List<RankInfo> rankInfos = new ArrayList<>();
        for (Exam studentExam : studentExams) {
            User user = userMapper.selectById(studentExam.getStudentId());
            RankInfo rankInfo = new RankInfo(null, classExamNum, null, schoolExamNum, user.getName(), studentExam.getRealScore());
            rankInfos.add(rankInfo);
        }

        return rankInfos;
    }

    @Override
    public Rate getSchoolRateByexamId(Long examId) {
        Double totalScore = examInfoMapper.selectById(examId).getTotalScore();
        Double passScore = totalScore * 0.6;
        Double excellentScore = totalScore * 0.8;

        LambdaQueryWrapper<Exam> queryWrapper = new LambdaQueryWrapper<>();
        int examCount = examMapper.selectCount(queryWrapper.eq(Exam::getExamInfoId, examId));
        int passCount = examMapper.selectCount(queryWrapper.ge(Exam::getRealScore, passScore));
        int noPassCount = examCount - passCount;
        queryWrapper.clear();
        int excellentCount = examMapper.selectCount(queryWrapper.eq(Exam::getExamInfoId, examId).ge(Exam::getRealScore, passScore));
        passCount = passCount - excellentCount;

        String noPassRate= DataAnalysisUtil.parseToPercentage(noPassCount,examCount);
        String passRate= DataAnalysisUtil.parseToPercentage(passCount,examCount);
        String excellenceRate= DataAnalysisUtil.parseToPercentage(excellentCount,examCount);

        return new Rate(noPassRate, passRate, excellenceRate);
    }

    @Override
    public Rate getClassRateByExamIdAndClassId(Long examId, Long classId) {
        Double totalScore = examInfoMapper.selectById(examId).getTotalScore();
        Double passScore = totalScore * 0.6;
        Double excellentScore = totalScore * 0.8;
        LambdaQueryWrapper<Exam> queryWrapper = new LambdaQueryWrapper<>();
        int examCount = examMapper.selectCount(queryWrapper.eq(Exam::getExamInfoId, examId).eq(Exam::getClassId, classId));
        int passCount = examMapper.selectCount(queryWrapper.ge(Exam::getRealScore, passScore));
        int noPassCount = examCount - passCount;
        queryWrapper.clear();
        int excellentCount = examMapper.selectCount(queryWrapper.eq(Exam::getExamInfoId, examId).eq(Exam::getClassId, classId).ge(Exam::getRealScore, passScore));
        passCount = passCount - excellentCount;

        String noPassRate= DataAnalysisUtil.parseToPercentage(noPassCount,examCount);
        String passRate= DataAnalysisUtil.parseToPercentage(passCount,examCount);
        String excellenceRate= DataAnalysisUtil.parseToPercentage(excellentCount,examCount);

        return new Rate(noPassRate, passRate, excellenceRate);
    }

    @Override
    public SchoolExamScore getSchoolScoreByExamId(Long examId) {
        ExamUtil examUtil = new ExamUtil();
        return examUtil.getSchoolExamScoreByExamId(examId);
    }

}
