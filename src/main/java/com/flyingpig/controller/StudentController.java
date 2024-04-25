package com.flyingpig.controller;

import com.flyingpig.common.Result;
import com.flyingpig.dataobject.dto.ClassStudent;
import com.flyingpig.dataobject.dto.LoginUser;
import com.flyingpig.dataobject.dto.StudentInfo;
import com.flyingpig.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@Api("与学生信息表相关的api")
public class StudentController {

    @Autowired
    IStudentService studentService;

    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public Result getStudentInfoById(@RequestHeader String Authorization){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        StudentInfo studentInfo=studentService.getStudentInfoById(loginUser.getUser().getId());
        return Result.success(studentInfo);
    }

    @GetMapping("/class-student/list")
    public Result getClassStudentByClassId(@RequestHeader String Authorization,@RequestParam Long classId){
        List<ClassStudent> classStudents=studentService.getClassStudentByClassId(classId);
        return Result.success(classStudents);
    }






}
