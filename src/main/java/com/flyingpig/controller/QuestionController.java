package com.flyingpig.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.common.Result;
import com.flyingpig.dataobject.entity.Question;
import com.flyingpig.service.IQuestionService;
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
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    IQuestionService questionService;
    @PutMapping("/score")
    @ApiOperation("批改试卷")
    public Result updateScoreByExamIdAndStudentId(@RequestParam Long examQuestionId, @RequestParam Long studentId,@RequestParam Double score){
        LambdaQueryWrapper<Question> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getStudentId,studentId).eq(Question::getQuestionInfoId,examQuestionId);
        Question studentQuestion=new Question();
        studentQuestion.setScore(score);
        questionService.update(studentQuestion,queryWrapper);
        return Result.success();
    }



}
