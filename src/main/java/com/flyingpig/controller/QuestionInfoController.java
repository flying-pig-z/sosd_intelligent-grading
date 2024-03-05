package com.flyingpig.controller;


import com.flyingpig.common.Result;
import com.flyingpig.dataobject.dto.QuestionAnalysis;
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
    public Result listQuestionAnalysisById(@RequestHeader String Authorization, @RequestParam Long examInfoId, @RequestParam Long classId) {
        List<QuestionAnalysis> questionAnalysisList = questionInfoService.listQuestionAnalysisById(examInfoId, classId);
        return Result.success(questionAnalysisList);
    }

}
