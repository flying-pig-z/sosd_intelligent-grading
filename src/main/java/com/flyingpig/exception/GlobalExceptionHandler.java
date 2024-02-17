package com.flyingpig.exception;

import com.alibaba.fastjson.JSON;
import com.flyingpig.common.Result;
import com.flyingpig.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//全局异常处理器
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public Result ex(AccessDeniedException ex) {
        log.error("身份权限不符合");
        ex.printStackTrace();
        return Result.error(0,"身份权限不符合");
    }
    @ExceptionHandler(Exception.class)
    public Result ex(Exception ex) {
        ex.printStackTrace();
        return Result.error(2,"对不起，操作失败，请联系管理员");
    }
}
