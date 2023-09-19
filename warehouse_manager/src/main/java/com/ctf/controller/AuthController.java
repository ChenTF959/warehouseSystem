package com.ctf.controller;

import com.ctf.entity.Auth;
import com.ctf.entity.Result;
import com.ctf.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/17 20:50
 * @description:
 */


@RequestMapping("/auth")
@RestController
public class  AuthController {

    //注入AuthService
    @Autowired
    private AuthService authService;

    //查询所有权限菜单shu的url接口/auth/auth-tree
    @RequestMapping("/auth-tree")
    public Result loadAllAuthTree() {
        List<Auth> allAuthTree = authService.allAuthTree();
        return Result.ok(allAuthTree);
    }


}
