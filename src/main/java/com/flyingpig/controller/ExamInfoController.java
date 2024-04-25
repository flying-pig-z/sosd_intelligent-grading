package com.flyingpig.controller;


import com.flyingpig.common.Result;
import com.flyingpig.dataobject.dto.LoginUser;
import com.flyingpig.dataobject.dto.StudentExamInfo;
import com.flyingpig.dataobject.vo.CreateExam;
import com.flyingpig.dataobject.vo.CreateQuestion;
import com.flyingpig.service.IExamInfoService;
import com.flyingpig.service.IExamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
@RestController
@RequestMapping("/exam-info")
public class ExamInfoController {

    @Autowired
    IExamInfoService examInfoService;
    @Autowired
    IExamService examService;

    @GetMapping("/search")
    @ApiOperation("搜索试卷")
    public Result seachExamInfoByKeyword(@RequestHeader String Authorization,
                                         @RequestParam(required = false) String time,
                                         @RequestParam(required = false) String type,
                                         @RequestParam(required = false) String subject,
                                         @RequestParam(required = false) String grade) {
        return Result.success(examInfoService.listExamInfoByKeyword(time, type, subject, grade));
    }

    @GetMapping("/{id}/question-task/list")
    @ApiOperation("获取该试卷的题目任务")
    public Result listQuestionTaskById(@RequestHeader String Authorization, @PathVariable Long id) {
        return Result.success(examInfoService.listQuestionTaskById(id));
    }

    @GetMapping("/student-exam/list")
    @ApiOperation("学生考试，时间由晚到早")
    public Result listStudentExamByStudentID(@RequestHeader String Authorization, @RequestParam Long studentId) {
        return Result.success(examService.listStudentExamByStudentId(studentId));
    }

    @PostMapping
    @ApiOperation("创建试卷")
    public Result createExam(@RequestHeader String Authorization, @RequestBody CreateExam createExam) {
        return Result.success(examService.createExam(createExam));
    }



    @GetMapping("/student-exam-list")
    @ApiOperation("列举学生考试列表")
    public Result listExamInfoByStudentId(@RequestHeader String Authorization) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<StudentExamInfo> studentExamInfos=examInfoService.listExamInfoByStudentId(loginUser.getUser().getId());
        return Result.success(studentExamInfos);
    }






}
