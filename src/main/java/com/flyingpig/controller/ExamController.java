package com.flyingpig.controller;

import com.flyingpig.common.Result;
import com.flyingpig.dataobject.dto.LoginUser;
import com.flyingpig.dataobject.dto.SubjectStatus;
import com.flyingpig.dataobject.dto.TotalScoreDTO;
import com.flyingpig.dataobject.dto.ScoreDistributionGraph;
import com.flyingpig.dataobject.entity.Exam;
import com.flyingpig.dataobject.vo.StudentExamVO;
import com.flyingpig.service.IExamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping("/score-statistics")
    @ApiOperation("获取学校和学校考试最高分和平均成绩")
    public Result getScoreStatisticsScoreByExamInfoId(@RequestHeader String Authorization, Long examInfoId, Long classId) {
        return Result.success(examService.getScoreStatisticsByExamInfoIdAndClassId(examInfoId, classId));
    }


    @GetMapping("/class-rank/list")
    @ApiOperation("获取班级成绩排行榜")
    public Result getClassRankListById(@RequestHeader String Authorization, @RequestParam Long examInfoId, @RequestParam Long classId) {
        return Result.success(examService.getClassRankListById(examInfoId, classId));
    }

    @GetMapping("/subject-status")
    @ApiOperation("获取考试学科和得分")
    public Result listSubjectStatusByStudentIdAndExamName(@RequestHeader String Authorization, String examName) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SubjectStatus> subjectStatusList = examService.listSubjectStatusByStudentIdAndExamName(loginUser.getUser().getId(), examName);
        return Result.success(subjectStatusList);
    }

    @PostMapping("/comment")
    @ApiOperation("老师评论某个试卷")
    public Result addComment(@RequestHeader String Authorization, String comment, Long examId) {
        examService.addComment(examId, comment);
        return Result.success();
    }

    @GetMapping("/comment")
    @ApiOperation("获取某个界面的教师评论")
    public Result getComment(@RequestHeader String Authorization, Long examId) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Exam exam = examService.getById(examId);
        return Result.success(exam.getComment());
    }

    @GetMapping("/total-score")
    @ApiOperation("获取总分")
    public Result getTotalScore(@RequestHeader String Authorization, String examName) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TotalScoreDTO totalScore = examService.getTotalScore(loginUser.getUser().getId(), examName);
        return Result.success(totalScore);
    }

    @GetMapping("score-distribution-graph")
    @ApiOperation("分数分布图像所需要的数据")
    public Result getScoreDistributionGraph(@RequestHeader String Authorization, Long examId, @RequestParam(required = false) Long classId) {
        List<ScoreDistributionGraph> scoreDistributionGraphs = examService.getScoreDistributionGraph(examId, classId);
        return Result.success(scoreDistributionGraphs);
    }

    @GetMapping("score-trend-graph")
    @ApiOperation("近十次的考试成绩")
    public Result getScoreTrendGraph(@RequestHeader String Authorization, Long studentId) {
        List<Double> scoreDistributionGraphs = examService.getScoreTrendGraph(studentId);
        return Result.success(scoreDistributionGraphs);
    }

    @PostMapping("")
    @ApiOperation("添加学生试卷")
    public Result addStudentExam(@RequestHeader String Authorization, @RequestBody StudentExamVO studentExamVO) {
        examService.addStudentExam(studentExamVO);
        return Result.success();
    }

}
