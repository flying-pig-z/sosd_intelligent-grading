package com.flyingpig.controller;


import com.flyingpig.common.Result;
import com.flyingpig.dataobject.dto.QuestionAnalysis;
import com.flyingpig.dataobject.dto.StudentQuestionAnalysis;
import com.flyingpig.dataobject.vo.CreateQuestion;
import com.flyingpig.service.IQuestionInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
@RestController
@RequestMapping("/question-info")
public class QuestionInfoController {

    @Autowired
    IQuestionInfoService questionInfoService;

    @GetMapping("/analysis-list")
    @ApiOperation("班级试卷分析")
    public Result listQuestionAnalysisById(@RequestHeader String Authorization, Long examInfoId, Long classId) {
        List<QuestionAnalysis> questionAnalysisList = questionInfoService.listQuestionAnalysisById(examInfoId, classId);
        return Result.success(questionAnalysisList);
    }

    @GetMapping("/student-analysis/list")
    @ApiOperation("学生试卷分析")
    public Result listStudentAnalysisByExamInfoIdAndStudentId(@RequestHeader String Authorization, Long examInfoId, Long studentId) {
        List<StudentQuestionAnalysis> questionAnalysisList = questionInfoService.listStudentAnalysisByExamInfoIdAndStudentId(examInfoId, studentId);
        return Result.success(questionAnalysisList);
    }

    @PostMapping
    @ApiOperation("题组上传")
    public Result addQuestionInfo(@RequestHeader String Authorization, Long examInfoId, @RequestBody List<CreateQuestion> createQuestionList) {
        questionInfoService.addQuestionInfo(examInfoId, createQuestionList);
        return Result.success();
    }




}
