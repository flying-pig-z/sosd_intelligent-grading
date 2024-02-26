package com.flyingpig.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.common.Result;
import com.flyingpig.dataobject.entity.StudentQuestionGroup;
import com.flyingpig.mapper.StudentQuestionGroupMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


public class StudentQuestionGroupController {

    @Autowired
    StudentQuestionGroupMapper studentQuestionGroupMapper;

    @GetMapping("/uncorrected-id")
    @ApiOperation("点击阅卷或者阅完一张试卷，获取下一张学生题组的id")
    public Result getUncorrectedIdByQuestionGroupId(Long questionGroupId){
        LambdaQueryWrapper<StudentQuestionGroup> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentQuestionGroup::getQuestionGroupId,questionGroupId).eq(StudentQuestionGroup::getCorrectOrder,0);
        List<StudentQuestionGroup> studentQuestionGroupList=studentQuestionGroupMapper.selectList(queryWrapper);
        long uncorrectedId=studentQuestionGroupList.get(0).getId();
        return Result.success(uncorrectedId);
    }



}
