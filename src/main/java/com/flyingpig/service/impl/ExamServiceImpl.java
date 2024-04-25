package com.flyingpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flyingpig.dataobject.dto.*;
import com.flyingpig.dataobject.entity.*;
import com.flyingpig.dataobject.entity.User;
import com.flyingpig.dataobject.vo.CreateExam;
import com.flyingpig.dataobject.vo.CreateQuestion;
import com.flyingpig.dataobject.vo.StudentExamVO;
import com.flyingpig.mapper.*;
import com.flyingpig.service.IExamService;
import com.flyingpig.util.DataAnalysisUtil;
import com.flyingpig.util.ExamUtil;
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
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements IExamService {

    @Autowired
    ExamInfoMapper examInfoMapper;
    @Autowired
    ExamMapper examMapper;

    @Autowired
    QuestionInfoMapper questionInfoMapper;

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

        String noPassRate = DataAnalysisUtil.parseToPercentage(noPassCount, examCount);
        String passRate = DataAnalysisUtil.parseToPercentage(passCount, examCount);
        String excellenceRate = DataAnalysisUtil.parseToPercentage(excellentCount, examCount);

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

        String noPassRate = DataAnalysisUtil.parseToPercentage(noPassCount, examCount);
        String passRate = DataAnalysisUtil.parseToPercentage(passCount, examCount);
        String excellenceRate = DataAnalysisUtil.parseToPercentage(excellentCount, examCount);

        return new Rate(noPassRate, passRate, excellenceRate);
    }

    @Override
    public ExamStatistics getScoreStatisticsByExamInfoIdAndClassId(Long examId, Long classId) {
        ExamStatistics examStatistics = new ExamStatistics();
        ExamScore schoolScore = examUtil.getSchoolExamScoreByExamId(examId);

        examStatistics.setSchoolHighestScore(schoolScore.getHighestScore());
        examStatistics.setSchoolPerScore(schoolScore.getPerScore());
        examStatistics.setSchoolTotalScore(schoolScore.getTotalScore());
        ExamScore classScore = examUtil.getClassExamScoreByExamIdAndClassId(examId, classId);
        examStatistics.setClassHighestScore(classScore.getHighestScore());
        examStatistics.setClassPerScore(classScore.getPerScore());
        examStatistics.setClassTotalScore(classScore.getTotalScore());
        return examStatistics;
    }

    @Override
    public List<StudentExam> listStudentExamByStudentId(Long studentId) {
        List<StudentExam> studentExams = new ArrayList<>();
        LambdaQueryWrapper<Exam> examLambdaQueryWrapper = new LambdaQueryWrapper<>();
        examLambdaQueryWrapper.eq(Exam::getStudentId, studentId);
        List<Exam> exams = examMapper.selectList(examLambdaQueryWrapper);
        for (Exam exam : exams) {
            StudentExam studentExam = new StudentExam();
            studentExam.setExamId(exam.getId());
            studentExam.setScore(exam.getScore());
            ExamInfo examInfo = examInfoMapper.selectById(exam.getExamInfoId());
            studentExam.setExamName(examInfo.getName());
            studentExam.setExamGrade(examInfo.getGrade());
            studentExam.setExamTime(examInfo.getDetailTime());
            studentExam.setSubject(examInfo.getSubject());
            studentExams.add(studentExam);
        }
        return studentExams;
    }

    @Override
    public List<ClassRank> getClassRankListById(Long examInfoId, Long classId) {
        LambdaQueryWrapper<Exam> examLambdaQueryWrapper = new LambdaQueryWrapper<>();
        examLambdaQueryWrapper.eq(Exam::getExamInfoId, examInfoId).orderByDesc(Exam::getScore);
        List<Exam> exams = examMapper.selectList(examLambdaQueryWrapper);
        List<ClassRank> classRanks = new ArrayList<>();
        for (int i = 0; i < exams.size(); i++) {
            Exam exam = exams.get(i);
            if (exam.getClassId().equals(classId)) {
                ClassRank classRank = new ClassRank();
                classRank.setSchoolRank((long) i + 1);
                classRank.setScore(exam.getScore());
                classRank.setStudentName(userMapper.selectById(exam.getStudentId()).getName());
                classRanks.add(classRank);
            }
        }
        return classRanks;
    }

    @Override
    public long createExam(CreateExam createExam) {
        ExamInfo examInfo = new ExamInfo(createExam);
        examInfoMapper.insert(examInfo);
        return examInfo.getId();

    }

    @Override
    public List<SubjectStatus> listSubjectStatusByStudentIdAndExamName(Long studentId, String examName) {
        LambdaQueryWrapper<ExamInfo> examInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        examInfoLambdaQueryWrapper.eq(ExamInfo::getName, examName);
        List<ExamInfo> examInfos = examInfoMapper.selectList(examInfoLambdaQueryWrapper);
        List<SubjectStatus> subjectStatusList = new ArrayList<>();
        for (ExamInfo examInfo : examInfos) {
            LambdaQueryWrapper<Exam> examLambdaQueryWrapper = new LambdaQueryWrapper<>();
            examLambdaQueryWrapper.eq(Exam::getStudentId, studentId).eq(Exam::getExamInfoId, examInfo.getId());
            Exam exam = examMapper.selectOne(examLambdaQueryWrapper);
            if (exam == null) {
                continue;
            }
            SubjectStatus subjectStatus = new SubjectStatus();
            subjectStatus.setExamId(exam.getId());
            subjectStatus.setSubject(examInfo.getSubject());
            subjectStatus.setScore(exam.getScore());
            subjectStatus.setTotalScore(examInfo.getTotalScore());
            subjectStatusList.add(subjectStatus);
        }

        return subjectStatusList;
    }

    @Override
    public void addComment(Long examId, String comment) {
        Exam exam = new Exam();
        exam.setId(examId);
        exam.setComment(comment);
        examMapper.updateById(exam);
    }

    @Override
    public TotalScoreDTO getTotalScore(Long studentId, String examName) {
        LambdaQueryWrapper<ExamInfo> examInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        examInfoLambdaQueryWrapper.eq(ExamInfo::getName, examName);
        List<ExamInfo> examInfos = examInfoMapper.selectList(examInfoLambdaQueryWrapper);
        Double getScore = Double.valueOf(0);
        Double totalScore = Double.valueOf(0);
        for (ExamInfo examInfo : examInfos) {
            LambdaQueryWrapper<Exam> examLambdaQueryWrapper = new LambdaQueryWrapper<>();
            examLambdaQueryWrapper.eq(Exam::getStudentId, studentId).eq(Exam::getExamInfoId, examInfo.getId());
            Exam exam = examMapper.selectOne(examLambdaQueryWrapper);
            if (exam == null) {
                continue;
            }
            getScore = getScore + exam.getScore();
            totalScore = totalScore + examInfo.getTotalScore();
        }
        return new TotalScoreDTO(getScore, totalScore);
    }

    @Override
    public List<ScoreDistributionGraph> getScoreDistributionGraph(Long examId, Long classId) {
        //先获取总分
        Exam exam = examMapper.selectById(examId);
        ExamInfo examInfo = examInfoMapper.selectById(exam.getExamInfoId());
        Double totalScore = examInfo.getTotalScore();
        List<ScoreDistributionGraph> result = new ArrayList<>();
        for (double i = 0; i < totalScore; i = i + totalScore / 10) {
            ScoreDistributionGraph scoreDistributionGraph = new ScoreDistributionGraph();
            if (classId == null) {
                scoreDistributionGraph.setCount(examUtil.getCountByScoreRange(i, i + totalScore / 10 - 1));
            } else {
                scoreDistributionGraph.setCount(examUtil.getCountByScoreRange(i, i + totalScore / 10 - 1, classId));
            }
            scoreDistributionGraph.setScoreRange(String.format("%.0f-%.0f", i, i + totalScore / 10 - 1));
            result.add(scoreDistributionGraph);
        }
        return result;
    }

    @Override
    public List<Double> getScoreTrendGraph(Long studentId) {
        //1.从学生的id获取考试名的集合
        LambdaQueryWrapper<Exam> examQueryWrapper = new LambdaQueryWrapper<>();
        examQueryWrapper.eq(Exam::getStudentId, studentId);
        List<Exam> exams = examMapper.selectList(examQueryWrapper);
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < 10 && i < exams.size(); i++) {
            result.add(exams.get(i).getScore());
        }
        return result;
    }

    @Override
    public void addStudentExam(StudentExamVO studentExamVO) {
        Exam exam = new Exam();
        BeanUtils.copyProperties(studentExamVO, exam);
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getNo, studentExamVO.getStudentNO());
        Long studentId = userMapper.selectOne(userLambdaQueryWrapper).getId();
        Student student=studentMapper.selectById(studentId);
        exam.setStudentId(student.getId());
        exam.setClassId(student.getClassId());
        ExamInfo examInfo = examInfoMapper.selectById(exam.getExamInfoId());
        examInfo.setNum(examInfo.getNum()+1);
        examInfoMapper.updateById(examInfo);
    }


}
