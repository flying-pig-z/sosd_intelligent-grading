package com.flyingpig.controller;

import com.flyingpig.common.Result;
import com.flyingpig.dataobject.constant.StatusCode;
import com.flyingpig.dataobject.entity.User;
import com.flyingpig.dataobject.vo.ChangePasswordVO;
import com.flyingpig.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api("与用户表相关的api")
public class UserController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result login(@RequestBody User user) {
        try {
            System.out.println(user);
            return loginService.login(user);
        } catch (Exception e) {
            System.out.println(e);
            return Result.error(StatusCode.PASSWORDERROR,"账号或密码错误，请重新登录");
        }
    }

    @PostMapping("/logout")
    @ApiOperation("用户登出")
    public Result logout() {
        return loginService.logout();
    }

    @PutMapping("/password")
    @ApiOperation("修改密码")
    public Result changePassword(@RequestBody ChangePasswordVO changePasswordVO) {
        // 从数据库中的密码
        String passwordInDatabase = loginService.getPasswordByNo(changePasswordVO.getNo());
        // 创建BCryptPasswordEncoder对象
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 判断传回来的旧密码是否与数据库中存储的密码一致
        if (passwordEncoder.matches(changePasswordVO.getOldPassword(), passwordInDatabase)) {
            // 对新密码进行加密
            String newEncodedPassword = passwordEncoder.encode(changePasswordVO.getNewPassword());
            // 更新数据库中的密码
            loginService.updateUserWithPassword(changePasswordVO.getNo(), newEncodedPassword);
            return Result.success("密码更新成功");
        } else {
            // 旧密码不匹配，返回错误提示
            return Result.error(StatusCode.PASSWORDERROR,"密码更新失败");
        }
    }

}
