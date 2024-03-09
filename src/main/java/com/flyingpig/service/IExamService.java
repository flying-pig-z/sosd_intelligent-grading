package com.flyingpig.service;

import com.flyingpig.dataobject.dto.ExamReport;
import com.flyingpig.dataobject.dto.Rate;
import com.flyingpig.dataobject.dto.RankInfo;
import com.flyingpig.dataobject.dto.ExamScore;
import com.baomidou.mybatisplus.extension.service.IService;
import com.flyingpig.dataobject.entity.Exam;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
public interface IExamService extends IService<Exam> {

    List<RankInfo> getRankByExamIdAndClassId(Long examId, Long classId);

    Rate getSchoolRateByExamInfoId(Long examId);

    Rate getClassRateByExamInfoIdAndClassId(Long examId, Long classId);

    ExamScore getSchoolScoreByExamInfoId(Long examId);
}
