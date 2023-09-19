package com.ctf.service;

import com.ctf.dto.AssignRoleDto;
import com.ctf.entity.Result;
import com.ctf.entity.Role;
import com.ctf.entity.User;
import com.ctf.page.Page;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/12 22:04
 * @description:use—info表的service接口
 */
public interface UserService {

    //根据账号查询用户的业务方法
    public User queryUserByCode(String userCode);

    //分页查询用户的业务方法
    public Page queryUserByPage(Page page, User user);

    //添加用户的业务方法
    public Result saveUser(User user);

    //启用或禁用用户的url接口的业务方法
    public Result setUserState(User user);

    //给用户分配角色的业务方法
    public void assignRole(AssignRoleDto assignRoleDto);

    //删除用户
    public Result removeUserByIds(List<Integer> userIdList);

    //修改用户的业务方法
    public Result setUserById(User user);

    //根据用户id修改密码
    public Result setPwdById(Integer userId);


}