package com.flyingpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.ExamReport;
import com.flyingpig.dataobject.dto.ExamScore;
import com.flyingpig.dataobject.dto.TeacherInfo;
import com.flyingpig.dataobject.entity.*;
import com.flyingpig.mapper.*;
import com.flyingpig.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Autowired
    TeacherClassRelationMapper teacherClassRelationMapper;
    @Autowired
    SchoolClassMapper schoolClassMapper;

    @Autowired
    ExamMapper examMapper;

    @Autowired
    ExamInfoMapper examInfoMapper;

    @Autowired
    ExamUtil examUtil;

    @Autowired
    UserMapper userMapper;

    @Override
    public TeacherInfo getTeacherInfoById(Long id) {
        Teacher teacher = this.getById(id);
        LambdaQueryWrapper<TeacherClassRelation> teacherClassRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teacherClassRelationLambdaQueryWrapper.eq(TeacherClassRelation::getTeacherId, id);
        List<TeacherClassRelation> list = teacherClassRelationMapper.selectList(teacherClassRelationLambdaQueryWrapper);
        TeacherInfo teacherInfo = new TeacherInfo(null, null, null, new ArrayList<>());
        User user = userMapper.selectById(id);
        BeanUtils.copyProperties(teacher, teacherInfo);
        BeanUtils.copyProperties(user, teacherInfo);
        for (TeacherClassRelation relation : list) {
            teacherInfo.getSchoolClassList().add(schoolClassMapper.selectById(relation.getClassId()));
        }
        return teacherInfo;
    }

    @Override
    public Set<ExamReport> listExamReportById(Long id) {
        LambdaQueryWrapper<TeacherClassRelation> teacherClassRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teacherClassRelationLambdaQueryWrapper.eq(TeacherClassRelation::getTeacherId, id);
        List<TeacherClassRelation> teacherClassRelations = teacherClassRelationMapper.selectList(teacherClassRelationLambdaQueryWrapper);
        Set<ExamReport> examReportSet = new HashSet<>();
        for (TeacherClassRelation teacherClassRelation : teacherClassRelations) {
            Long classId = teacherClassRelation.getClassId();
            LambdaQueryWrapper<Exam> examLambdaQueryWrapper = new LambdaQueryWrapper<>();
            examLambdaQueryWrapper.eq(Exam::getClassId, classId);
            List<Exam> exams = examMapper.selectList(examLambdaQueryWrapper);
            for (Exam exam : exams) {
                LambdaQueryWrapper<ExamInfo> examInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
                examInfoLambdaQueryWrapper.eq(ExamInfo::getId, exam.getExamInfoId());
                List<ExamInfo> examInfos = examInfoMapper.selectList(examInfoLambdaQueryWrapper);
                for (ExamInfo examInfo : examInfos) {
                    ExamReport examReport = new ExamReport();
                    examReport.setExamInfoId(examInfo.getId());
                    BeanUtils.copyProperties(examInfo, examReport);
                    ExamScore schoolExamScore = examUtil.getSchoolExamScoreByExamId(examInfo.getId());
                    examReport.setPerScoreOfSchool(schoolExamScore.getPerScore());
                    examReportSet.add(examReport);
                }
            }
        }
        return examReportSet;
    }


}
