package com.flyingpig.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.QuestionGroupTask;
import com.flyingpig.dataobject.dto.TotalAndCorrectedAmount;
import com.flyingpig.dataobject.entity.ExamInfo;
import com.flyingpig.dataobject.entity.QuestionGroupInfo;
import com.flyingpig.dataobject.entity.QuestionGroup;
import com.flyingpig.mapper.ExamInfoMapper;
import com.flyingpig.mapper.QuestionGroupInfoMapper;
import com.flyingpig.mapper.QuestionGroupMapper;
import com.flyingpig.service.IQuestionGroupInfoService;
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
public class QuestionGroupServiceImpl implements IQuestionGroupInfoService {

    @Autowired
    ExamInfoMapper examMapper;
    @Autowired
    QuestionGroupInfoMapper questionGroupMapper;
    @Autowired
    QuestionGroupMapper studentQuestionGroupMapper;

    @Override
    public List<QuestionGroupTask> getTeacherQuestionGroupByExamId(Long examId) {
        List<QuestionGroupTask> result =new ArrayList<>();
        ExamInfo exam=examMapper.selectById(examId);
        LambdaQueryWrapper<QuestionGroupInfo> questionGroupLambdaQueryWrapper=new LambdaQueryWrapper<>();
        questionGroupLambdaQueryWrapper.eq(QuestionGroupInfo::getExamInfoId,exam.getId());
        List<QuestionGroupInfo> questionGroups=questionGroupMapper.selectList(questionGroupLambdaQueryWrapper);
        for(QuestionGroupInfo questionGroup:questionGroups){
            QuestionGroupTask teacherQuestionGroup=new QuestionGroupTask();
            BeanUtils.copyProperties(questionGroup,teacherQuestionGroup);
            BeanUtils.copyProperties(exam,teacherQuestionGroup);
            result.add(teacherQuestionGroup);
        }
        return result;
    }

    @Override
    public TotalAndCorrectedAmount getTotalAndRemainingAmountById(Long id) {
        TotalAndCorrectedAmount totalAndCorrectedAmount=new TotalAndCorrectedAmount();
        LambdaQueryWrapper<QuestionGroup> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(QuestionGroup::getQuestionGroupInfoId,id);
        totalAndCorrectedAmount.setTotalAccount(studentQuestionGroupMapper.selectCount(queryWrapper).longValue());
        queryWrapper.select(QuestionGroup::getCorrectOrder).orderByDesc(QuestionGroup::getCorrectOrder).last("limit 1");
        totalAndCorrectedAmount.setCorrectAccount(studentQuestionGroupMapper.selectOne(queryWrapper).getCorrectOrder());
        return totalAndCorrectedAmount;
    }
}
