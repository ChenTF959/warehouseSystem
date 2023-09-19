package com.ctf.mapper;

import com.ctf.entity.UserRole;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/15 9:54
 * @description:
 */
public interface UserRoleMapper {

    //根据用户id删除用户已分配的角色关系
    public int removeUserRoleByUid(Integer userId);

    //增加用户角色关系
    public int insertUserRole(UserRole userRole);
}
