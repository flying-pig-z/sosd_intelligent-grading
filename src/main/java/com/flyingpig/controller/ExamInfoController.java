package com.flyingpig.controller;


import com.flyingpig.common.Result;
import com.flyingpig.service.IExamInfoService;
import com.flyingpig.service.IExamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation("获取该试卷的题组任务")
    public Result listQuestionTaskById(@RequestHeader String Authorization,@PathVariable Long id) {
        return Result.success(examInfoService.listQuestionTaskById(id));
    }

}
