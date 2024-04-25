package com.flyingpig.dataobject.constant;

/**
 * 状态码
 */
public class StatusCode {
    /**
     * 操作成功
     */
    public static final int OK = 200;
    /**
     * 用户名或密码错误
     */
    public static final int PASSWORDERROR = 202;
    /**
     * token过期
     */
    public static final int TOKENEXPIREE = 203;

    /**
     * 请求参数异常或缺失
     */
    public static final int PARAMETERERROR = 400;

    /*
    * 认证错误
     */
    public static final int AUTHERROR = 405;

    /**
     * 权限不足
     */
    public static final int ACCESSERROR = 403;


    /**
     * 请求方法错误
     */
    public static final int METHODERROR = 405;

    /**
     * 服务端错误
     */
    public static final int SERVERERROR = 500;

    /**
     * 资源不存在
     */
    public static final int NOTFOUND = 404;

}
