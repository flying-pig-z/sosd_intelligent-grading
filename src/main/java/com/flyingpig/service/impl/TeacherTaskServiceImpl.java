package com.flyingpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.ExamTask;
import com.flyingpig.dataobject.dto.QuestionTask;
import com.flyingpig.dataobject.entity.ExamInfo;
import com.flyingpig.dataobject.entity.QuestionInfo;
import com.flyingpig.dataobject.entity.TeacherTask;
import com.flyingpig.mapper.ExamInfoMapper;
import com.flyingpig.mapper.QuestionInfoMapper;
import com.flyingpig.mapper.TeacherTaskMapper;
import com.flyingpig.service.ITeacherTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flyingpig.util.ExamUtil;
import com.flyingpig.util.QuestionUtil;
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
public class TeacherTaskServiceImpl extends ServiceImpl<TeacherTaskMapper, TeacherTask> implements ITeacherTaskService {

    @Autowired
    TeacherTaskMapper teacherTaskMapper;
    @Autowired
    ExamInfoMapper examInfoMapper;

    @Autowired
    QuestionInfoMapper questionInfoMapper;


    @Autowired
    ExamUtil examUtil;

    @Autowired
    QuestionUtil questionUtil;


    @Override
    public List<ExamTask> listExamTaskByTeacherId(Long id) {
        LambdaQueryWrapper<TeacherTask> teacherTaskLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teacherTaskLambdaQueryWrapper.eq(TeacherTask::getTeacherId, id);
        List<TeacherTask> teacherTaskList = teacherTaskMapper.selectList(teacherTaskLambdaQueryWrapper);
        List<ExamTask> result = new ArrayList<>();
        for (TeacherTask teacherTask : teacherTaskList) {
            Long examId = teacherTask.getExamInfoId();
            ExamInfo exam = examInfoMapper.selectById(examId);
            ExamTask examTask = new ExamTask();
            BeanUtils.copyProperties(exam, examTask);
            result.add(examTask);
        }
        return result;
    }

    @Override
    public List<QuestionTask> listQuestionTaskByTeacherId(Long id) {
        LambdaQueryWrapper<TeacherTask> teacherTaskLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teacherTaskLambdaQueryWrapper.eq(TeacherTask::getTeacherId, id);
        List<TeacherTask> teacherTaskList = teacherTaskMapper.selectList(teacherTaskLambdaQueryWrapper);
        List<QuestionTask> result = new ArrayList<>();
        for (TeacherTask teacherTask : teacherTaskList) {
            ExamInfo examInfo = examInfoMapper.selectById(teacherTask.getExamInfoId());
            LambdaQueryWrapper<QuestionInfo> questionGroupLambdaQueryWrapper = new LambdaQueryWrapper<>();
            questionGroupLambdaQueryWrapper.eq(QuestionInfo::getExamInfoId, examInfo.getId());
            List<QuestionInfo> questionGroups = questionInfoMapper.selectList(questionGroupLambdaQueryWrapper);
            for (QuestionInfo questionInfo : questionGroups) {
                QuestionTask questionTask = new QuestionTask(examInfo,questionInfo,questionUtil.getUncorrectedAndCorrectedQuestionNum(questionInfo.getId()));
                result.add(questionTask);
            }
        }
        return result;
    }


}
