package com.flyingpig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.flyingpig.dataobject.dto.TeacherInfo;
import com.flyingpig.dataobject.entity.Teacher;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
@Service
public interface ITeacherService extends IService<Teacher> {

    TeacherInfo getTeacherInfoById(Long id);
}
