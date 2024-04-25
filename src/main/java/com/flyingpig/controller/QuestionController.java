package com.flyingpig.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.common.Result;
import com.flyingpig.dataobject.dto.AnswerSituation;
import com.flyingpig.dataobject.dto.LoginUser;
import com.flyingpig.dataobject.dto.QuestionAnalysis;
import com.flyingpig.dataobject.dto.QuestionScoreInfo;
import com.flyingpig.dataobject.entity.Question;
import com.flyingpig.dataobject.vo.ScoreQuestion;
import com.flyingpig.dataobject.vo.StudentExamVO;
import com.flyingpig.service.IExamService;
import com.flyingpig.service.IQuestionService;
import com.flyingpig.service.impl.QuestionServiceImpl;
import com.flyingpig.util.QuestionUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    IQuestionService questionService;
    @Autowired
    QuestionUtil questionUtil;

    @Autowired
    IExamService examService;

    @PutMapping("")
    @ApiOperation("批改试卷")
    public Result update(@RequestHeader String Authorization, @RequestBody ScoreQuestion scoreQuestion){
        questionService.updateScoreAndCommentByScoreQuestion(scoreQuestion);
        return Result.success();
    }

    @GetMapping("/score-info")
    @ApiOperation("获取一张试卷进行批改")
    public Result getQuestionByQuestionInfoId(@RequestHeader String Authorization, @RequestParam Long questionInfoId) {
        QuestionScoreInfo questionScoreInfo = questionService.getQuestionScoreInfoByQuestionInfoId(questionInfoId);
        return Result.success(questionScoreInfo);
    }

    @GetMapping("/answer-situation/list")
    @ApiOperation("移动端获取题目答题情况")
    public Result getAnswerSituationByStudentIdAndQuestionInfoId(@RequestHeader String Authorization, Long examInfoId) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<AnswerSituation> answerSituations=questionService.getAnswerSituationByStudentIdAndQuestionInfoId(loginUser.getUser().getId(),
                examInfoId);
        return Result.success(answerSituations);
    }

    @PostMapping("")
    @ApiOperation("添加学生题目")
    public Result addStudentExam(@RequestHeader String Authorization, @RequestBody StudentExamVO studentExamVO) {
        examService.addStudentExam(studentExamVO);
        return Result.success();
    }




}
