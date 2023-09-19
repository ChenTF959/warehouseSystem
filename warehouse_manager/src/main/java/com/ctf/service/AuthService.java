package com.ctf.service;

import com.ctf.entity.Auth;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/13 11:07
 * @description:
 */
public interface AuthService {

    //根据用户id查询用户权限(菜单)树的业务方法
    public List<Auth> authTreeByUid(Integer userId);


    //查询所有权限菜单树的业务方法
    public List<Auth> allAuthTree();


}
