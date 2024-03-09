package com.flyingpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.QuestionTask;
import com.flyingpig.dataobject.entity.ExamInfo;
import com.flyingpig.dataobject.entity.QuestionInfo;
import com.flyingpig.mapper.ExamInfoMapper;
import com.flyingpig.mapper.QuestionInfoMapper;
import com.flyingpig.service.IExamInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flyingpig.util.DataAnalysisUtil;
import com.flyingpig.util.QuestionUtil;
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
public class ExamInfoServiceImpl extends ServiceImpl<ExamInfoMapper, ExamInfo> implements IExamInfoService {

    @Autowired
    ExamInfoMapper examInfoMapper;

    @Autowired
    QuestionInfoMapper questionInfoMapper;

    @Autowired
    QuestionUtil questionUtil;

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
}
