package com.flyingpig.controller;

import com.flyingpig.common.Result;
import com.flyingpig.dataobject.dto.ExamReport;
import com.flyingpig.dataobject.dto.LoginUser;
import com.flyingpig.dataobject.dto.TeacherInfo;
import com.flyingpig.service.ITeacherClassRelationService;
import com.flyingpig.service.ITeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/teachers")
@Api("与教师相关的api")
public class TeacherController {
    @Autowired
    ITeacherService teacherService;
    @Autowired
    ITeacherClassRelationService teacherClassRelationService;

    @GetMapping("/info")
    @ApiOperation("教师获取个人信息")
    public Result getTeacherInfoById(@RequestHeader String Authorization){
        LoginUser loginUser=(LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TeacherInfo teacherInfo=teacherService.getTeacherInfoById(loginUser.getUser().getId());
        return Result.success(teacherInfo);
    }

    @GetMapping("/exam-report/list")
    @ApiOperation("教师获取班级报告列表")
    public Result getExamReportById(@RequestHeader String Authorization) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<ExamReport> examReports = teacherService.listExamReportById(loginUser.getUser().getId());
        return Result.success(examReports);
    }



}
