package com.flyingpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.QuestionTask;
import com.flyingpig.dataobject.dto.StudentExam;
import com.flyingpig.dataobject.dto.StudentExamInfo;
import com.flyingpig.dataobject.entity.Exam;
import com.flyingpig.dataobject.entity.ExamInfo;
import com.flyingpig.dataobject.entity.QuestionInfo;
import com.flyingpig.mapper.ExamInfoMapper;
import com.flyingpig.mapper.ExamMapper;
import com.flyingpig.mapper.QuestionInfoMapper;
import com.flyingpig.service.IExamInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.*;
import com.flyingpig.util.QuestionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
@Service
public class ExamInfoServiceImpl extends ServiceImpl<ExamInfoMapper, ExamInfo> implements IExamInfoService {

    @Autowired
    ExamInfoMapper examInfoMapper;

    @Autowired
    QuestionInfoMapper questionInfoMapper;

    @Autowired
    QuestionUtil questionUtil;

    @Autowired
    ExamMapper examMapper;

    @Override
    public List<ExamInfo> listExamInfoByKeyword(String time, String type, String subject, String grade) {
        LambdaQueryWrapper<ExamInfo> examLambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(time != null){
            examLambdaQueryWrapper.eq(ExamInfo::getApproxTime,time);
        }
        if(type != null){
            examLambdaQueryWrapper.eq(ExamInfo::getType,type);
        }
        if(subject!=null){
            examLambdaQueryWrapper.eq(ExamInfo::getSubject,subject);
        }
        if(grade!=null){
            examLambdaQueryWrapper.eq(ExamInfo::getGrade,grade);
        }
        return examInfoMapper.selectList(examLambdaQueryWrapper);
    }


    @Override
    public List<QuestionTask> listQuestionTaskById(Long id) {
        List<QuestionTask> result=new ArrayList<>();
        ExamInfo examInfo = examInfoMapper.selectById(id);
        LambdaQueryWrapper<QuestionInfo> questionGroupLambdaQueryWrapper = new LambdaQueryWrapper<>();
        questionGroupLambdaQueryWrapper.eq(QuestionInfo::getExamInfoId, examInfo.getId());
        List<QuestionInfo> questionGroups = questionInfoMapper.selectList(questionGroupLambdaQueryWrapper);
        for (QuestionInfo questionInfo : questionGroups) {
            QuestionTask questionTask = new QuestionTask(examInfo,questionInfo
                    , questionUtil.getUncorrectedAndCorrectedQuestionNum(questionInfo.getId()));

            result.add(questionTask);
        }
        return result;
    }

    @Override
    public Set<StudentExamInfo> listExamInfoByStudentId(Long id) {
        LambdaQueryWrapper<Exam> examLambdaQueryWrapper=new LambdaQueryWrapper<>();
        examLambdaQueryWrapper.eq(Exam::getStudentId,id);
        List<Exam> exams=examMapper.selectList(examLambdaQueryWrapper);
        Set<StudentExamInfo> studentExamInfos=new HashSet<>();
        for(Exam exam:exams){
            Long examInfoId=exam.getExamInfoId();
            ExamInfo examInfo=examInfoMapper.selectById(examInfoId);
            StudentExamInfo studentExamInfo=new StudentExamInfo();
            studentExamInfo.setExamTime(examInfo.getDetailTime());
            studentExamInfo.setName(examInfo.getName());
            studentExamInfos.add(studentExamInfo);
        }
        return studentExamInfos;
    }


}
