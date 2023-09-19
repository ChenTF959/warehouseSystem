package com.ctf.exception;

/**
 * @Author: ChenTF
 * @Date: 2023/9/12 20:30
 * @description:
 */
public class BusinessException extends RuntimeException{

    //只是创建异常对象
    public BusinessException() {
        super();
    }

    //创建异常对象同时指定异常信息
    public BusinessException(String message) {
        super(message);
    }
}