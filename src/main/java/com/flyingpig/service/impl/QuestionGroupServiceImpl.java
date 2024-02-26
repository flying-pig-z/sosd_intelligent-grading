package com.flyingpig.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.TeacherQuestionGroup;
import com.flyingpig.dataobject.dto.TotalAndCorrectedAmount;
import com.flyingpig.dataobject.entity.Exam;
import com.flyingpig.dataobject.entity.QuestionGroup;
import com.flyingpig.dataobject.entity.StudentQuestionGroup;
import com.flyingpig.mapper.ExamMapper;
import com.flyingpig.mapper.QuestionGroupMapper;
import com.flyingpig.mapper.StudentQuestionGroupMapper;
import com.flyingpig.service.IQuestionGroupService;
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
 * @since 2024-02-22
 */
@Service
public class QuestionGroupServiceImpl implements IQuestionGroupService {

    @Autowired
    ExamMapper examMapper;
    @Autowired
    QuestionGroupMapper questionGroupMapper;
    @Autowired
    StudentQuestionGroupMapper studentQuestionGroupMapper;

    @Override
    public List<TeacherQuestionGroup> getTeacherQuestionGroupByExamId(Long examId) {
        List<TeacherQuestionGroup> result =new ArrayList<>();
        Exam exam=examMapper.selectById(examId);
        LambdaQueryWrapper<QuestionGroup> questionGroupLambdaQueryWrapper=new LambdaQueryWrapper<>();
        questionGroupLambdaQueryWrapper.eq(QuestionGroup::getExamId,exam.getId());
        List<QuestionGroup> questionGroups=questionGroupMapper.selectList(questionGroupLambdaQueryWrapper);
        for(QuestionGroup questionGroup:questionGroups){
            TeacherQuestionGroup teacherQuestionGroup=new TeacherQuestionGroup();
            BeanUtils.copyProperties(questionGroup,teacherQuestionGroup);
            BeanUtils.copyProperties(exam,teacherQuestionGroup);
            result.add(teacherQuestionGroup);
        }
        return result;
    }

    @Override
    public TotalAndCorrectedAmount getTotalAndRemainingAmountById(Long id) {
        TotalAndCorrectedAmount totalAndCorrectedAmount=new TotalAndCorrectedAmount();
        LambdaQueryWrapper<StudentQuestionGroup> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentQuestionGroup::getQuestionGroupId,id);
        totalAndCorrectedAmount.setTotalAccount(studentQuestionGroupMapper.selectCount(queryWrapper).longValue());
        queryWrapper.select(StudentQuestionGroup::getCorrectOrder).orderByDesc(StudentQuestionGroup::getCorrectOrder).last("limit 1");
        totalAndCorrectedAmount.setCorrectAccount(studentQuestionGroupMapper.selectOne(queryWrapper).getCorrectOrder());
        return totalAndCorrectedAmount;
    }
}
