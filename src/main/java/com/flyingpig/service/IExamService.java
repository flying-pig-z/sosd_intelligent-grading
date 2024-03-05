package com.flyingpig.service;

import com.flyingpig.dataobject.dto.ExamReport;
import com.flyingpig.dataobject.dto.Rate;
import com.flyingpig.dataobject.dto.RankInfo;
import com.flyingpig.dataobject.dto.SchoolExamScore;
import com.flyingpig.dataobject.entity.Exam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
public interface IExamService extends IService<Exam> {

    ExamReport getExamReportByExamId(Long examId, Long classId);

    List<RankInfo> getRankByExamIdAndClassId(Long examId, Long classId);

    Rate getSchoolRateByexamId(Long examId);

    Rate getClassRateByExamIdAndClassId(Long examId, Long classId);

    SchoolExamScore getSchoolScoreByExamId(Long examId);
}
