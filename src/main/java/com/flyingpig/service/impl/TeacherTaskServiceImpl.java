package com.flyingpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.ExamTask;
import com.flyingpig.dataobject.dto.QuestionGroupTask;
import com.flyingpig.dataobject.entity.ExamInfo;
import com.flyingpig.dataobject.entity.QuestionGroupInfo;
import com.flyingpig.dataobject.entity.TeacherTask;
import com.flyingpig.mapper.ExamInfoMapper;
import com.flyingpig.mapper.QuestionGroupInfoMapper;
import com.flyingpig.mapper.TeacherTaskMapper;
import com.flyingpig.service.ITeacherTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class TeacherTaskServiceImpl extends ServiceImpl<TeacherTaskMapper, TeacherTask> implements ITeacherTaskService {

    @Autowired
    TeacherTaskMapper teacherTaskMapper;
    @Autowired
    ExamInfoMapper examMapper;

    @Autowired
    QuestionGroupInfoMapper questionGroupMapper;

    @Autowired
    ExamUtil examUtil;


    @Override
    public List<ExamTask> listExamTaskByTeacherId(Long id) {
        LambdaQueryWrapper<TeacherTask> teacherTaskLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teacherTaskLambdaQueryWrapper.eq(TeacherTask::getTeacherId, id);
        List<TeacherTask> teacherTaskList = teacherTaskMapper.selectList(teacherTaskLambdaQueryWrapper);
        List<ExamTask> result = new ArrayList<>();
        for (TeacherTask teacherTask : teacherTaskList) {
            Long examId = teacherTask.getExamId();
            ExamInfo exam = examMapper.selectById(examId);
            ExamTask examTask = new ExamTask();
            BeanUtils.copyProperties(exam, examTask);
            examTask.setSchedule(examUtil.getCompleteRateByExamId(examId));
            result.add(examTask);
        }
        return result;
    }

    @Override
    public List<QuestionGroupTask> listQuestionGroupTaskByTeacherId(Long id) {
        LambdaQueryWrapper<TeacherTask> teacherTaskLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teacherTaskLambdaQueryWrapper.eq(TeacherTask::getTeacherId, id);
        List<TeacherTask> teacherTaskList = teacherTaskMapper.selectList(teacherTaskLambdaQueryWrapper);
        List<QuestionGroupTask> result = new ArrayList<>();
        for (TeacherTask teacherTask : teacherTaskList) {
            ExamInfo exam = examMapper.selectById(teacherTask.getExamId());
            LambdaQueryWrapper<QuestionGroupInfo> questionGroupLambdaQueryWrapper = new LambdaQueryWrapper<>();
            questionGroupLambdaQueryWrapper.eq(QuestionGroupInfo::getExamInfoId, exam.getId());
            List<QuestionGroupInfo> questionGroups = questionGroupMapper.selectList(questionGroupLambdaQueryWrapper);
            for (QuestionGroupInfo questionGroup : questionGroups) {
                QuestionGroupTask teacherQuestionGroup = new QuestionGroupTask();
                BeanUtils.copyProperties(questionGroup, teacherQuestionGroup);
                BeanUtils.copyProperties(exam, teacherQuestionGroup);
                result.add(teacherQuestionGroup);
            }
        }
        return result;
    }


}
