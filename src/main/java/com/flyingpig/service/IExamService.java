package com.flyingpig.service;

import com.flyingpig.dataobject.dto.*;
import com.baomidou.mybatisplus.extension.service.IService;
import com.flyingpig.dataobject.entity.Exam;
import com.flyingpig.dataobject.vo.CreateExam;
import com.flyingpig.dataobject.vo.CreateQuestion;
import com.flyingpig.dataobject.vo.StudentExamVO;

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

    List<RankInfo> getRankByExamIdAndClassId(Long examId, Long classId);

    Rate getSchoolRateByExamInfoId(Long examId);

    Rate getClassRateByExamInfoIdAndClassId(Long examId, Long classId);

    ExamStatistics getScoreStatisticsByExamInfoIdAndClassId(Long examId, Long classId);

    List<StudentExam> listStudentExamByStudentId(Long studentId);
    List<ClassRank> getClassRankListById(Long examInfoId, Long classId);

    long createExam(CreateExam createExam);

    List<SubjectStatus> listSubjectStatusByStudentIdAndExamName(Long studentId, String examName);

    void addComment(Long examId,String comment);

    TotalScoreDTO getTotalScore(Long id, String examName);

    List<ScoreDistributionGraph> getScoreDistributionGraph(Long examId, Long classId);

    List<Double> getScoreTrendGraph(Long studentId);

    void addStudentExam(StudentExamVO studentExamVO);
}
