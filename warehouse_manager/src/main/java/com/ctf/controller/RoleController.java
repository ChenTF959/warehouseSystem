package com.ctf.controller;

import com.ctf.dto.AssignAuthDto;
import com.ctf.entity.Auth;
import com.ctf.entity.CurrentUser;
import com.ctf.entity.Result;
import com.ctf.entity.Role;
import com.ctf.page.Page;
import com.ctf.service.RoleService;
import com.ctf.utils.TokenUtils;
import com.ctf.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/13 21:08
 * @description:
 */


@RequestMapping("/role")
@RestController
public class RoleController {
    //注入RoleService
    @Autowired
    private RoleService roleService;

    //注入TokenUtils
    @Autowired
    private TokenUtils tokenUtils;

    //查询所有角色的url接口/role/role-list
    @RequestMapping("/role-list")
    public Result roleList() {
        //执行业务
        List<Role> roleList = roleService.queryAllRole();
        return Result.ok(roleList);
    }


    //分页查询角色的url接口/role/role-page-list
    @RequestMapping("/role-page-list")
    public Result roleListPage(Page page, Role role){
        page = roleService.queryRolePage(page, role);
        return Result.ok(page);
    }

    //添加角色的url接口/role/role-add
    @RequestMapping("/role-add")
    public Result addRole(@RequestBody Role role, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token ) {
        //拿到当前登录的用户的id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int createBy = currentUser.getUserId();

        role.setCreateBy(createBy);
        Result result = roleService.saveRole(role);
        return result;
    }

    //启用或禁用角色的url接口/role/role-state-update
    @RequestMapping("/role-state-update")
    public Result updateRoleState(@RequestBody Role role) {
        Result result = roleService.setRoleState(role);
        return result;
    }

    //删除角色的url接口/role/role-delete/{roleId}
    @RequestMapping("/role-delete/{roleId}")
    public Result deleteRole(@PathVariable Integer roleId) {
        Result result = roleService.deleteRoleById(roleId);
        return result;
    }


    //查询角色分配的所有权限菜单的url接口/role/role-auth
    @RequestMapping("/role-auth")
    public Result roleAuth(Integer roleId) {
        List<Integer> authIdList = roleService.queryRoleAuthIds(roleId);
        return Result.ok(authIdList);
    }

    //给角色分配菜单权限的url接口/role/auth-grant
    @RequestMapping("/auth-grant")
    public Result grantAuth(@RequestBody AssignAuthDto assignAuthDto) {
        roleService.saveRoleAuth(assignAuthDto);
        return Result.ok("权限分配成功！");
    }

    //修改角色的url接口/role/role-update
    @RequestMapping("/role-update")
    public Result updateRole(@RequestBody Role role, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {

        //拿到当前登录的用户id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int updateBy = currentUser.getUserId();

        //执行业务
        Result result = roleService.setRoleByRid(role);
        return result;
    }



}
