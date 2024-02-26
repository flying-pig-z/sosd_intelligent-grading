package com.flyingpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.entity.Exam;
import com.flyingpig.mapper.ExamMapper;
import com.flyingpig.service.IExamService;
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
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements IExamService {

    @Autowired
    ExamMapper examMapper;

    @Override
    public List<Exam> listExamInfoByKeyword(String time, String type, String subject, String grade) {
        LambdaQueryWrapper<Exam> examLambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(time != null){
            examLambdaQueryWrapper.eq(Exam::getTime,time);
        }
        if(type != null){
            examLambdaQueryWrapper.eq(Exam::getType,type);
        }
        if(subject!=null){
            examLambdaQueryWrapper.eq(Exam::getSubject,subject);
        }
        if(grade!=null){
            examLambdaQueryWrapper.eq(Exam::getGrade,grade);
        }
        return examMapper.selectList(examLambdaQueryWrapper);
    }
}
