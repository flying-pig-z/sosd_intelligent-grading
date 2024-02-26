package com.flyingpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.TeacherQuestionGroup;
import com.flyingpig.dataobject.entity.Exam;
import com.flyingpig.dataobject.entity.ExamQuestion;
import com.flyingpig.dataobject.entity.QuestionGroup;
import com.flyingpig.dataobject.entity.TeacherTask;
import com.flyingpig.mapper.ExamMapper;
import com.flyingpig.mapper.ExamQuestionMapper;
import com.flyingpig.mapper.QuestionGroupMapper;
import com.flyingpig.mapper.TeacherTaskMapper;
import com.flyingpig.service.ITeacherTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
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
public class TeacherTaskServiceImpl extends ServiceImpl<TeacherTaskMapper, TeacherTask> implements ITeacherTaskService {

    @Autowired
    TeacherTaskMapper teacherTaskMapper;
    @Autowired
    ExamMapper examMapper;

    @Autowired
    QuestionGroupMapper questionGroupMapper;

    @Override
    public List<Exam> getTeacherExamTaskById(Long id) {
        LambdaQueryWrapper<TeacherTask> teacherTaskLambdaQueryWrapper=new LambdaQueryWrapper<>();
        teacherTaskLambdaQueryWrapper.eq(TeacherTask::getTeacherId,id);
        List<TeacherTask> teacherTaskList= teacherTaskMapper.selectList(teacherTaskLambdaQueryWrapper);
        List<Exam> result=new ArrayList<>();
        for(TeacherTask teacherTask:teacherTaskList){
            result.add(examMapper.selectById(teacherTask.getExamId()));
        }
        return result;
    }

    @Override
    public List<TeacherQuestionGroup> getTeacherQuestionGroupByTeacherId(Long id) {
        LambdaQueryWrapper<TeacherTask> teacherTaskLambdaQueryWrapper=new LambdaQueryWrapper<>();
        teacherTaskLambdaQueryWrapper.eq(TeacherTask::getTeacherId,id);
        List<TeacherTask> teacherTaskList= teacherTaskMapper.selectList(teacherTaskLambdaQueryWrapper);
        List<TeacherQuestionGroup> result=new ArrayList<>();
        for(TeacherTask teacherTask:teacherTaskList){
            Exam exam=examMapper.selectById(teacherTask.getExamId());
            LambdaQueryWrapper<QuestionGroup> questionGroupLambdaQueryWrapper=new LambdaQueryWrapper<>();
            questionGroupLambdaQueryWrapper.eq(QuestionGroup::getExamId,exam.getId());
            List<QuestionGroup> questionGroups=questionGroupMapper.selectList(questionGroupLambdaQueryWrapper);
            for(QuestionGroup questionGroup:questionGroups){
                TeacherQuestionGroup teacherQuestionGroup=new TeacherQuestionGroup();
                BeanUtils.copyProperties(questionGroup,teacherQuestionGroup);
                BeanUtils.copyProperties(exam,teacherQuestionGroup);
                result.add(teacherQuestionGroup);
            }
        }
        return result;
    }


}
