package com.ctf.controller;

import com.ctf.dto.AssignRoleDto;
import com.ctf.entity.*;
import com.ctf.page.Page;
import com.ctf.service.RoleService;
import com.ctf.service.UserService;
import com.ctf.utils.TokenUtils;
import com.ctf.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/13 15:20
 * @description:
 */

@RequestMapping("/user")
@RestController
public class UserController {

    //注入USerService
    @Autowired
    private UserService userService;

    //分页查询用户的url接口/user/user-list
    @RequestMapping("/user-list")
    public Result userList(Page page, User user) {
         page = userService.queryUserByPage(page, user);
         return Result.ok(page);
    }

    //添加用户的url接口/user/addUser
    //参数@RequestBody User user -- 接收并封装前端传递的json数据的用户信息
    //注入TokenUtils
    @Autowired
    private TokenUtils tokenUtils;

    @RequestMapping("/addUser")
    public Result addUser(@RequestBody User user, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //拿到当前登录的用户id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int createdBy = currentUser.getUserId();
         user.setCreateBy(createdBy);

        Result result = userService.saveUser(user);
        return result;
    }

    //启用或禁用用户的url接口
    @RequestMapping("updateState")
    public Result updateUserState(@RequestBody User user) {
        Result result = userService.setUserState(user);
        return result;
    }

    @Autowired
    private RoleService roleService;
    //查询用户已分配的角色的url接口/user/user-role-list{userId}
    //参数@PathVariable Integer userId -- 表示将路径占位符userId的值(用户id)赋值给请求处理方法入参变量userId
    @RequestMapping("/user-role-list/{userId}")
    public Result userRoleList(@PathVariable Integer userId) {
        //执行业务
        List<Role> roleList = roleService.queryUserRoleByUid(userId);
        //响应
        return Result.ok(roleList);
    }

    //给用户分配角色的url接口/user/assignRole
    @RequestMapping("/assignRole")
    private Result assignRole(@RequestBody AssignRoleDto assignRoleDto) {
        //执行业务
        userService.assignRole(assignRoleDto);
        return Result.ok("分配角色成功");
    }

    //根据用户id删除单个用户的url接口/user/deleteUser/{userId}
    @RequestMapping("/deleteUser/{userId}")
    public Result removeUserById(@PathVariable Integer userId) {
        Result result = userService.removeUserByIds(Arrays.asList(userId));
        return result;
    }

    //根据用户ids批量删除用户的url接口/user/deleteUserList
    @RequestMapping("/deleteUserList")
    public Result deleteUserByIds(@RequestBody List<Integer> userIdList) {
        Result result = userService.removeUserByIds(userIdList);
        return result;
    }

    //修改用户的url接口/user/updateUser
    @RequestMapping("/updateUser")
    public Result updateUser(@RequestBody User user, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //拿到当前登录的用户id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int updateBy = currentUser.getUserId();
        user.setUpdateBy(updateBy);

        Result result = userService.setUserById(user);
        return result;

    }


    //重置密码的url接口/user/updatePwd/{userId}
    @RequestMapping("/updatePwd/{userId}")
    public Result resetPassword(@PathVariable Integer userId){
        Result result = userService.setPwdById(userId);
        return result;
    }

}
