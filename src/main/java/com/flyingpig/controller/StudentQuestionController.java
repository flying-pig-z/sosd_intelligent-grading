package com.flyingpig.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.common.Result;
import com.flyingpig.dataobject.entity.StudentQuestion;
import com.flyingpig.service.IStudentQuestionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
@RestController
@RequestMapping("/student-question")
public class StudentQuestionController {
    @Autowired
    IStudentQuestionService studentQuestionService;
    @PutMapping("/score")
    @ApiOperation("批改试卷")
    public Result updateScoreByExamIdAndStudentId(@RequestParam Long examQuestionId, @RequestParam Long studentId,@RequestParam Double score){
        LambdaQueryWrapper<StudentQuestion> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentQuestion::getStudentId,studentId).eq(StudentQuestion::getExamQuestionId,examQuestionId);
        StudentQuestion studentQuestion=new StudentQuestion();
        studentQuestion.setScore(score);
        studentQuestionService.update(studentQuestion,queryWrapper);
        return Result.success();
    }



}
