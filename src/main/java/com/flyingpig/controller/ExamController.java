package com.flyingpig.controller;

import com.flyingpig.common.Result;
import com.flyingpig.dataobject.dto.ExamReport;
import com.flyingpig.service.IExamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    IExamService examService;

    @GetMapping("/exam-report")
    @ApiOperation("获取班级报告表图数据")
    public Result getExamReportByExamId(@RequestHeader String Authorization, @RequestParam Long examInfoId, @RequestParam Long classId) {
        ExamReport examReport = examService.getExamReportByExamId(examInfoId, classId);
        return Result.success();
    }

    @GetMapping("/school-rate")
    @ApiOperation("获取学校考试及格率和优秀率")
    public Result getSchoolRankByExamId(@RequestHeader String Authorization, @RequestParam Long examInfoId) {
        return Result.success(examService.getSchoolRateByexamId(examInfoId));
    }

    @GetMapping("/class-rate")
    @ApiOperation("获取班级考试及格率和优秀率")
    public Result getClassRankByExamIdAndClassId(@RequestHeader String Authorization, @RequestParam Long examInfoId, @RequestParam Long classId) {
        return Result.success(examService.getClassRateByExamIdAndClassId(examInfoId, classId));
    }


    @GetMapping("/school-score")
    @ApiOperation("获取学校考试最高分和平均成绩")
    public Result getSchoolScoreByExamId(@RequestHeader String Authorization, @RequestParam Long examInfoId) {
        return Result.success(examService.getSchoolScoreByExamId(examInfoId));
    }

}
