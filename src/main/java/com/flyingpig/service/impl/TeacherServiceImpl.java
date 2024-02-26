package com.flyingpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.TeacherInfo;
import com.flyingpig.dataobject.entity.Teacher;
import com.flyingpig.dataobject.entity.TeacherClassRelation;
import com.flyingpig.dataobject.entity.User;
import com.flyingpig.mapper.SchoolClassMapper;
import com.flyingpig.mapper.TeacherClassRelationMapper;
import com.flyingpig.mapper.TeacherMapper;
import com.flyingpig.mapper.UserMapper;
import com.flyingpig.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Autowired
    TeacherClassRelationMapper teacherClassRelationMapper;
    @Autowired
    SchoolClassMapper schoolClassMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public TeacherInfo getTeacherInfoById(Long id) {
        Teacher teacher=this.getById(id);
        LambdaQueryWrapper<TeacherClassRelation> teacherClassRelationLambdaQueryWrapper=new LambdaQueryWrapper<>();
        teacherClassRelationLambdaQueryWrapper.eq(TeacherClassRelation::getTeacherId,id);
        List<TeacherClassRelation> list=teacherClassRelationMapper.selectList(teacherClassRelationLambdaQueryWrapper);
        TeacherInfo teacherInfo=new TeacherInfo(null,null,null,new ArrayList<>());
        User user=userMapper.selectById(id);
        BeanUtils.copyProperties(teacher,teacherInfo);
        BeanUtils.copyProperties(user,teacherInfo);
        for(TeacherClassRelation relation : list){
            teacherInfo.getSchoolClassList().add(schoolClassMapper.selectById(relation.getClassId()));
        }
        return teacherInfo;
    }




}
