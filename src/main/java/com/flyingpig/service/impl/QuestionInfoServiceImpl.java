package com.flyingpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.QuestionAnalysis;
import com.flyingpig.dataobject.dto.StudentQuestionAnalysis;
import com.flyingpig.dataobject.entity.Question;
import com.flyingpig.dataobject.entity.QuestionInfo;
import com.flyingpig.dataobject.vo.CreateQuestion;
import com.flyingpig.mapper.QuestionInfoMapper;
import com.flyingpig.mapper.QuestionMapper;
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
 * 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
@Service
public class QuestionInfoServiceImpl extends ServiceImpl<QuestionInfoMapper, QuestionInfo> implements IQuestionInfoService {

    @Autowired
    QuestionInfoMapper questionInfoMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionUtil questionUtil;

    @Override
    public List<QuestionAnalysis> listQuestionAnalysisById(Long examId, Long classId) {
        LambdaQueryWrapper<QuestionInfo> questionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        questionLambdaQueryWrapper.eq(QuestionInfo::getExamInfoId, examId);
        List<QuestionInfo> questionInfos = questionInfoMapper.selectList(questionLambdaQueryWrapper);
        List<QuestionAnalysis> analysisList = new ArrayList<>();
        for (QuestionInfo questionInfo : questionInfos) {
            QuestionAnalysis questionAnalysis = new QuestionAnalysis();
            BeanUtils.copyProperties(questionInfo, questionAnalysis);
            analysisList.add(questionAnalysis);
            questionAnalysis.setSchoolPerScore(questionUtil.getSchoolPerScore(questionInfo.getId()));
            questionAnalysis.setClassPerScore(questionUtil.getClassPerScore(questionInfo.getId(), classId));
        }
        return analysisList;
    }

    @Override
    public List<StudentQuestionAnalysis> listStudentAnalysisByExamInfoIdAndStudentId(Long examInfoId, Long studentId) {
        LambdaQueryWrapper<QuestionInfo> questionInfoQueryWrapper = new LambdaQueryWrapper<>();
        questionInfoQueryWrapper.eq(QuestionInfo::getExamInfoId, examInfoId);
        List<QuestionInfo> questionInfos = questionInfoMapper.selectList(questionInfoQueryWrapper);
        List<StudentQuestionAnalysis> analysisList = new ArrayList<>();
        for (QuestionInfo questionInfo : questionInfos) {
            StudentQuestionAnalysis questionAnalysis = new StudentQuestionAnalysis();
            questionAnalysis.setContent(questionInfo.getContent());
            questionAnalysis.setReferenceAnswer(questionInfo.getReferenceAnswer());
            questionAnalysis.setSchoolPerScore(questionUtil.getSchoolPerScore(questionInfo.getId()));
            LambdaQueryWrapper<Question> questionWrapper = new LambdaQueryWrapper<>();
            questionWrapper.eq(Question::getStudentId, studentId).eq(Question::getQuestionInfoId, questionInfo.getId());
            Question question = questionMapper.selectOne(questionWrapper);
            if (question != null) {
                questionAnalysis.setStudentAnswer(question.getAnswer());
                questionAnalysis.setStudentScore(question.getScore());

            }
            analysisList.add(questionAnalysis);

        }
        return analysisList;
    }

    @Override
    public void addQuestionInfo(Long examInfoId, List<CreateQuestion> createQuestionList) {
        for (CreateQuestion createQuestion : createQuestionList) {
            QuestionInfo questionInfo = new QuestionInfo();
            BeanUtils.copyProperties(createQuestion, questionInfo);
            questionInfo.setExamInfoId(examInfoId);
            questionInfoMapper.insert(questionInfo);
        }
    }


}
