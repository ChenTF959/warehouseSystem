package com.ctf.service;

import com.ctf.dto.AssignAuthDto;
import com.ctf.entity.Auth;
import com.ctf.entity.Result;
import com.ctf.entity.Role;
import com.ctf.page.Page;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/13 21:03
 * @description:
 */
public interface RoleService {

    //查询所有角色的业务方法
    public List<Role> queryAllRole();

    //根据用户id查询用户分配的所有角色
    public List<Role> queryUserRoleByUid(Integer userId);

    //分页查询角色
    public Page queryRolePage(Page page, Role role);

    //添加角色的业务方法
    public Result saveRole(Role role);

    //启用或禁用角色
    public Result setRoleState(Role role);


    //删除角色
    public Result deleteRoleById(Integer roleId);

    //查询角色分配的所有权限菜单的id
    public List<Integer> queryRoleAuthIds(Integer roleId);

    //给角色分配权限
    public void saveRoleAuth(AssignAuthDto assignAuthDto);

    //修改角色
    public Result setRoleByRid(Role role);


}
