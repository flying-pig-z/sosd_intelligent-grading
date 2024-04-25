package com.flyingpig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.flyingpig.dataobject.dto.ClassStudent;
import com.flyingpig.dataobject.entity.SchoolClass;
import com.flyingpig.dataobject.entity.Student;
import com.flyingpig.dataobject.dto.StudentInfo;
import com.flyingpig.dataobject.entity.User;
import com.flyingpig.mapper.SchoolClassMapper;
import com.flyingpig.mapper.StudentMapper;
import com.flyingpig.mapper.UserMapper;
import com.flyingpig.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SchoolClassMapper schoolClassMapper;

    @Override
    public List<ClassStudent> getClassStudentByClassId(Long classId) {
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper=new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.eq(Student::getClassId, classId);
        List<Student> studentList=studentMapper.selectList(studentLambdaQueryWrapper);
        List<ClassStudent> classStudents=new ArrayList<>();
        for(Student student:studentList){
            ClassStudent classStudent=new ClassStudent();
            classStudent.setId(student.getId());
            User user=userMapper.selectById(student.getId());
            classStudent.setName(user.getName());
            classStudents.add(classStudent);
        }
        return classStudents;
    }

    @Override
    public StudentInfo getStudentInfoById(Long id) {
        User user=userMapper.selectById(id);
        Student student=studentMapper.selectById(id);
        StudentInfo studentInfo=new StudentInfo();
        studentInfo.setName(user.getName());
        SchoolClass schoolClass=schoolClassMapper.selectById(id);
        studentInfo.setClassName(schoolClass.getName());
        studentInfo.setGrade(schoolClass.getName().substring(0,2));
        return studentInfo;
    }
}
