package com.flyingpig.controller;

import com.flyingpig.common.Result;
import com.flyingpig.service.TeacherClassService;
import com.flyingpig.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/teachers")
@Api("与教师相关的api")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    TeacherClassService teacherClassService;

    @GetMapping("/info")
    @ApiOperation("教师获取个人信息")
    public Result getTeacherInfo(){

        return Result.success();
    }

}
