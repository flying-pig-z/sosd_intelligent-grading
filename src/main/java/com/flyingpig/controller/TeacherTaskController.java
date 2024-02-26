package com.flyingpig.controller;


import com.flyingpig.common.Result;
import com.flyingpig.dataobject.dto.LoginUser;
import com.flyingpig.dataobject.dto.TeacherQuestionGroup;
import com.flyingpig.dataobject.entity.Exam;
import com.flyingpig.service.ITeacherTaskService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/teacher-task")
public class TeacherTaskController {

    @Autowired
    ITeacherTaskService teacherTaskService;

    @GetMapping("/exam")
    @ApiOperation("教师试卷任务")
    public Result getExamTaskByTeacherId(@RequestHeader String Authorization){
        LoginUser loginUser=(LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Exam> exam = teacherTaskService.getTeacherExamTaskById(loginUser.getUser().getId());
        return Result.success(exam);
    }

    @GetMapping("/question_group")
    @ApiOperation("教师题组任务")
    public Result getTeacherQuestionGroupByTeacherId(@RequestHeader String Authorization){
        LoginUser loginUser=(LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TeacherQuestionGroup> questionGroups = teacherTaskService.getTeacherQuestionGroupByTeacherId(loginUser.getUser().getId());
        return Result.success(questionGroups);
    }
}