package com.flyingpig.controller;


import com.flyingpig.common.Result;
import com.flyingpig.dataobject.dto.ExamReport;
import com.flyingpig.service.IExamService;
import com.flyingpig.service.IStudentExamService;
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
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    IExamService examService;
    @Autowired
    IStudentExamService studentExamService;

    @GetMapping("/list")
    public Result listExamInfoByKeyword(@RequestHeader String Authorization,
                                        @RequestParam(required = false) String time,
                                        @RequestParam(required = false) String type,
                                        @RequestParam(required = false) String subject,
                                        @RequestParam(required = false) String grade){
        return Result.success(examService.listExamInfoByKeyword(time,type ,subject ,grade ));
    }



    @GetMapping("/exam-report")
    @ApiOperation("获取班级报告表图数据")
    public Result getExamReportByExamId(@RequestParam Long examId, @RequestParam Long classId){
        ExamReport examReport=studentExamService.getExamReportByExamId(examId, classId);
        return Result.success();
    }

    @GetMapping("/school-rank")
    public Result getSchoolRankByExamId(@RequestParam Long examId){
        return Result.success(studentExamService.getSchoolRankByexamId(examId));
    }

    @GetMapping("/class-rank")
    public Result getClassRankByExamIdAndClassId(@RequestParam Long examId,@RequestParam Long classId){
        return Result.success(studentExamService.getClassRankByExamIdAndClassId(examId,classId));
    }


    @GetMapping("/school-score")
    public Result getSchoolScoreByExamId(@RequestParam Long examId){
        return Result.success(studentExamService.getSchoolScoreByExamId(examId));
    }

}
