package com.ctf.mapper;

import com.ctf.entity.RoleAuth;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/17 20:03
 * @description:
 */
public interface RoleAuthMapper {

    //根据角色id查询角色分配的所有菜单权限id
    public List<Integer> findAuthIdByRid(Integer roleId);

    //根据角色id删除角色权限关系
    public int removeRoleAuthByRid(Integer roleId);

    //添加角色权限关系
    public int insertRoleAuth(RoleAuth roleAuth);


}
