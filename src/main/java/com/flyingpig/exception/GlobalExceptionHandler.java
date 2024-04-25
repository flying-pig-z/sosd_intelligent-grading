package com.flyingpig.exception;

import com.baomidou.mybatisplus.extension.api.R;
import com.flyingpig.common.Result;
import com.flyingpig.dataobject.constant.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.BindException;

//全局异常处理器
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 请求参数异常/缺少--400
     *
     * @param e
     * @return
     */
    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            RequestRejectedException.class,
            BindException.class}
    )
    public Result missingServletRequestParameterException(Exception e) {
        return Result.error(StatusCode.PARAMETERERROR, "缺少参数或参数错误");
    }


    /**
     * 认证错误--401
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    public Result authenticationExceptionException(Exception e) {
        return Result.error(StatusCode.AUTHERROR, "缺少参数或参数错误");
    }


    /**
     * 权限不足--403
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result accessDeniedExceptionHandler(Exception e) {
        log.error("权限不足");
        return Result.error(StatusCode.ACCESSERROR,"权限不足");
    }

    /**
     * 请求方法错误--405
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result httpRequestMethodNotSupportedExceptionHandler(Exception e){
        log.error("请求方法错误");
        return Result.error(StatusCode.METHODERROR,"请求方法错误");
    }


    /**
     * 其他异常--500
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        e.printStackTrace();
        return Result.error(StatusCode.SERVERERROR,"对不起，操作失败，请联系管理员");
    }
}
