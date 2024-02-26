package com.flyingpig.controller;

import com.flyingpig.common.Result;
import com.flyingpig.dataobject.dto.TeacherQuestionGroup;
import com.flyingpig.service.IQuestionGroupService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question-group")
public class QuestionGroupController {

    @Autowired
    IQuestionGroupService questionGroupService;

    @GetMapping
    public Result getTeacherQuestionGroupByExamId(@RequestHeader String Authorization,@RequestParam Long examId){
        List<TeacherQuestionGroup> teacherQuestionGroupList=questionGroupService.getTeacherQuestionGroupByExamId(examId);
        return Result.success();
    }

    @ApiOperation("获取题组总的有多少份试卷和还剩多少份试卷")
    @GetMapping("/account")
    public Result getTotalAndRemainingAmountById(@RequestHeader String Authorization,@RequestParam Long id){
        return Result.success(questionGroupService.getTotalAndRemainingAmountById(id));
    }
}
