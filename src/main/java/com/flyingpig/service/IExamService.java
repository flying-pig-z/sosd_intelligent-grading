package com.flyingpig.service;

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

    List<Exam> listExamInfoByKeyword(String time, String type, String subject, String grade);
}
