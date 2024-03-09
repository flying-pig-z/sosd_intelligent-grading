package com.flyingpig.controller;

import com.flyingpig.common.Result;
import com.flyingpig.dataobject.dto.ExamReport;
import com.flyingpig.dataobject.dto.LoginUser;
import com.flyingpig.service.IExamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    IExamService examService;

    @GetMapping("/school-rate")
    @ApiOperation("获取学校考试及格率和优秀率")
    public Result getSchoolRankByExamInfoId(@RequestHeader String Authorization, @RequestParam Long examInfoId) {
        return Result.success(examService.getSchoolRateByExamInfoId(examInfoId));
    }

    @GetMapping("/class-rate")
    @ApiOperation("获取班级考试及格率和优秀率")
    public Result getClassRankByExamInfoIdAndClassId(@RequestHeader String Authorization, @RequestParam Long examInfoId, @RequestParam Long classId) {
        return Result.success(examService.getClassRateByExamInfoIdAndClassId(examInfoId, classId));
    }


    @GetMapping("/school-score")
    @ApiOperation("获取学校考试最高分和平均成绩")
    public Result getSchoolScoreByExamInfoId(@RequestHeader String Authorization, @RequestParam Long examInfoId) {
        return Result.success(examService.getSchoolScoreByExamInfoId(examInfoId));
    }

}
