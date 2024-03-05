package com.flyingpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.QuestionAnalysis;
import com.flyingpig.dataobject.entity.QuestionInfo;
import com.flyingpig.mapper.QuestionInfoMapper;
import com.flyingpig.service.IQuestionInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flyingpig.util.QuestionUtil;
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
public class QuestionInfoServiceImpl extends ServiceImpl<QuestionInfoMapper, QuestionInfo> implements IQuestionInfoService {

    @Autowired
    QuestionInfoMapper questionMapper;
    @Autowired
    QuestionUtil questionUtil;

    @Override
    public List<QuestionAnalysis> listQuestionAnalysisById(Long examId, Long classId) {
        LambdaQueryWrapper<QuestionInfo> questionLambdaQueryWrapper=new LambdaQueryWrapper<>();
        questionLambdaQueryWrapper.eq(QuestionInfo::getExamInfoId,examId);
        List<QuestionInfo> questionInfos=questionMapper.selectList(questionLambdaQueryWrapper);
        List<QuestionAnalysis> analysisList=new ArrayList<>();
        for(QuestionInfo questionInfo:questionInfos){
            QuestionAnalysis questionAnalysis=new QuestionAnalysis();
            BeanUtils.copyProperties(questionInfo,questionAnalysis);
            analysisList.add(questionAnalysis);
            questionAnalysis.setSchoolPerScore(questionUtil.getSchoolPerScore(questionInfo.getId()));
            questionAnalysis.setClassPerScore(questionUtil.getClassPerScore(questionInfo.getId(),classId));
        }
        return analysisList;
    }
}
