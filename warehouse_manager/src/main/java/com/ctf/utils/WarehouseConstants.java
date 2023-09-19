package com.ctf.utils;

/**
 * 常量类:定义的内容是全局常量的接口
 */
public interface WarehouseConstants {

    //用户未审核
    public String USER_STATE_NOT_PASS = "0";

    //用户已审核
    public String USER_STATE_PASS = "1";

    //传递token的请求头名称
    public String HEADER_TOKEN_NAME = "Token";
}