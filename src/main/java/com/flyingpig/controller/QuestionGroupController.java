package com.flyingpig.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.common.Result;
import com.flyingpig.dataobject.entity.QuestionGroup;
import com.flyingpig.mapper.QuestionGroupMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


public class QuestionGroupController {

    @Autowired
    QuestionGroupMapper studentQuestionGroupMapper;

    @GetMapping("/uncorrected-id")
    @ApiOperation("点击阅卷或者阅完一张试卷，获取下一张学生题组的id")
    public Result getUncorrectedIdByQuestionGroupId(Long questionGroupId){
        LambdaQueryWrapper<QuestionGroup> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(QuestionGroup::getQuestionGroupInfoId,questionGroupId).eq(QuestionGroup::getCorrectOrder,0);
        List<QuestionGroup> studentQuestionGroupList=studentQuestionGroupMapper.selectList(queryWrapper);
        long uncorrectedId=studentQuestionGroupList.get(0).getId();
        return Result.success(uncorrectedId);
    }



}
