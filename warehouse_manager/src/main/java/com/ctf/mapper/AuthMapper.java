package com.ctf.mapper;

import com.ctf.entity.Auth;
import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/13 11:06
 * @description:
 */

public interface AuthMapper {

    //根据用户id查询用户所有权限(菜单)的方法
    public List<Auth> findAuthByUid(Integer userId);


    //查询所有权限菜单
    public List<Auth> findAllAuth();



}
