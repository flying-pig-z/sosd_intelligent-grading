package com.flyingpig.controller;

import com.flyingpig.dataobject.dto.LoginUser;
import com.flyingpig.dataobject.entity.Student;
import com.flyingpig.dataobject.entity.User;
import com.flyingpig.util.JwtUtil;
import com.flyingpig.common.Result;
import com.flyingpig.service.StudentService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/students")
@Api("与学生信息表相关的api")
public class StudentController {
    @Autowired
    private StudentService studentService;


}
