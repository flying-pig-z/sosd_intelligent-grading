package com.flyingpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.ExamReport;
import com.flyingpig.dataobject.dto.Rank;
import com.flyingpig.dataobject.dto.RankInfo;
import com.flyingpig.dataobject.dto.SchoolExamScore;
import com.flyingpig.dataobject.entity.StudentExam;
import com.flyingpig.dataobject.entity.User;
import com.flyingpig.mapper.ExamMapper;
import com.flyingpig.mapper.StudentExamMapper;
import com.flyingpig.mapper.StudentMapper;
import com.flyingpig.mapper.UserMapper;
import com.flyingpig.service.IStudentExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
@Service
public class StudentExamServiceImpl extends ServiceImpl<StudentExamMapper, StudentExam> implements IStudentExamService {

    @Autowired
    ExamMapper examMapper;
    @Autowired
    StudentExamMapper studentExamMapper;
    @Autowired
    StudentMapper studentMapper;

    @Autowired
    UserMapper userMapper;

    public Integer getExamNum(Long examId){
        return studentExamMapper.selectCount(
                new LambdaQueryWrapper<StudentExam>().eq(StudentExam::getExamId, examId)
        );
    }

    public Integer getClassExamNum(Long examId,Long classId){
        return studentExamMapper.selectCount(
                new LambdaQueryWrapper<StudentExam>().eq(StudentExam::getExamId, examId).eq(StudentExam::getClassId,classId)
        );
    }


    @Override
    public ExamReport getExamReportByExamId(Long examId, Long classId) {
        return null;
    }

    @Override
    public List<RankInfo> getRankByExamIdAndClassId(Long examId, Long classId) {
        Long schoolExamNum=Long.parseLong(getExamNum(examId).toString());
        Long classExamNum=Long.parseLong(getClassExamNum(examId,classId).toString());

        LambdaQueryWrapper<StudentExam> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentExam::getExamId,examId).eq(StudentExam::getClassId,classId).orderByDesc(StudentExam::getRealScore);
        List<StudentExam> studentExams=studentExamMapper.selectList(queryWrapper);

        List<RankInfo> rankInfos=new ArrayList<>();
        for(StudentExam studentExam : studentExams){
            User user=userMapper.selectById(studentExam.getStudentId());
            RankInfo rankInfo=new RankInfo(studentExam.getClassRank(),classExamNum,studentExam.getSchoolRank(),schoolExamNum, user.getName(),studentExam.getRealScore());
            rankInfos.add(rankInfo);
        }

        return rankInfos;
    }

    @Override
    public Rank getSchoolRankByexamId(Long examId) {
        Double totalScore = examMapper.selectById(examId).getTotalScore();
        Double passScore = totalScore * 0.6;
        Double excellentScore = totalScore * 0.8;
        LambdaQueryWrapper<StudentExam> queryWrapper=new LambdaQueryWrapper<>();
        int examCount=studentExamMapper.selectCount(queryWrapper.eq(StudentExam::getExamId,examId));
        int passCount=studentExamMapper.selectCount(queryWrapper.ge(StudentExam::getRealScore, passScore));
        queryWrapper.clear();
        int excellentCount=studentExamMapper.selectCount(queryWrapper.eq(StudentExam::getExamId,examId).ge(StudentExam::getRealScore, passScore));
        return new Rank((double)passCount/examCount,(double)excellentCount/examCount);
    }

    @Override
    public Rank getClassRankByExamIdAndClassId(Long examId, Long classId) {
        Double totalScore = examMapper.selectById(examId).getTotalScore();
        Double passScore = totalScore * 0.6;
        Double excellentScore = totalScore * 0.8;
        LambdaQueryWrapper<StudentExam> queryWrapper=new LambdaQueryWrapper<>();
        int examCount=studentExamMapper.selectCount(queryWrapper.eq(StudentExam::getExamId,examId).eq(StudentExam::getClassId,classId));
        int passCount=studentExamMapper.selectCount(queryWrapper.ge(StudentExam::getRealScore, passScore));
        queryWrapper.clear();
        int excellentCount=studentExamMapper.selectCount(queryWrapper.eq(StudentExam::getExamId,examId).eq(StudentExam::getClassId,classId).ge(StudentExam::getRealScore, passScore));
        return new Rank((double)passCount/examCount,(double)excellentCount/examCount);
    }

    @Override
    public SchoolExamScore getSchoolScoreByExamId(Long examId) {
        double totalScore = examMapper.selectById(examId).getTotalScore();
        LambdaQueryWrapper<StudentExam> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentExam::getExamId,examId).orderByDesc(StudentExam::getRealScore);
        List<StudentExam> studentExams=studentExamMapper.selectList(queryWrapper);
        Double highestScore=studentExams.get(0).getRealScore();
        double scoreSum=0;
        for(StudentExam studentExam:studentExams){
            scoreSum=scoreSum+studentExam.getRealScore();
        }
        double perScore=scoreSum/studentExams.size();
        return new SchoolExamScore(highestScore,perScore,totalScore);
    }

}
