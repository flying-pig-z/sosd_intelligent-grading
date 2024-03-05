package com.flyingpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.QuestionAnalysis;
import com.flyingpig.dataobject.entity.ExamInfo;
import com.flyingpig.mapper.ExamInfoMapper;
import com.flyingpig.service.IExamInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<ExamInfo> listExamInfoByKeyword(String time, String type, String subject, String grade) {
        LambdaQueryWrapper<ExamInfo> examLambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(time != null){
            examLambdaQueryWrapper.eq(ExamInfo::getTime,time);
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
    public List<QuestionAnalysis> listQuestionAnalysisById(Long examId) {

        return null;
    }
}
