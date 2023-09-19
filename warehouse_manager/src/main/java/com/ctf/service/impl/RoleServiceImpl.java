package com.ctf.service.impl;

import com.ctf.dto.AssignAuthDto;
import com.ctf.entity.Auth;
import com.ctf.entity.Result;
import com.ctf.entity.Role;
import com.ctf.entity.RoleAuth;
import com.ctf.mapper.AuthMapper;
import com.ctf.mapper.RoleAuthMapper;
import com.ctf.mapper.RoleMapper;
import com.ctf.page.Page;
import com.ctf.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/13 21:03
 * @description:
 */

//2 指定缓存的名称(数据保存到redis中键的前缀,一般值给标注@CacheConfig注解类的全路径)
@CacheConfig(cacheNames = "com.ctf.service.impl.RoleServiceImpl")
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleAuthMapper roleAuthMapper;

    //查询所有角色的业务方法
    //3 查询方法上标注@Cacheable指定缓存的键
    @Cacheable(key = "'all:role'")
    @Override
    public List<Role> queryAllRole() {
        return roleMapper.findAllRole();
    }


    //根据用户id查询用户分配的所有角色
    @Override
    public List<Role> queryUserRoleByUid(Integer userId) {
        return roleMapper.findUserRoleByUid(userId);
    }


    //分页查询角色
    @Override
    public Page queryRolePage(Page page, Role role) {

        //查询角色行数
        Integer count = roleMapper.findRoleRowCount(role);

        //分页查询角色
        List<Role> roleList = roleMapper.findRolePage(page, role);

        //组装分页信息
        page.setTotalNum(count);
        page.setResultList(roleList);
        return page;
    }


    //添加角色的业务方法
    @CacheEvict(key = "'all:role'")//清理redis缓存
    @Override
    public Result saveRole(Role role) {

        //判断角色是否已存在
        Role r = roleMapper.findRoleByNameOrCode(role.getRoleName(), role.getRoleCode());
        if (r != null) { //角色已存在
            return Result.err(Result.CODE_ERR_BUSINESS, "角色已存在！");
        }
        //角色不存在
        int i = roleMapper.insertRole(role);
        if (i > 0) {
            return Result.ok("角色添加成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "角色添加失败！");
    }


    //启用或禁用角色
    @CacheEvict(key = "'all:role'")//清理redis缓存
    @Override
    public Result setRoleState(Role role) {
        int i = roleMapper.setRoleStateByRid(role.getRoleId(), role.getRoleState());
        if (i > 0) {
            return Result.ok("角色启用或禁用成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "角色启用或禁用失败");
    }


    //删除角色
    @CacheEvict(key = "'all:role'")//清理redis缓存
    @Override
    public Result deleteRoleById(Integer roleId) {
        int i = roleMapper.removeRoleById(roleId);
        if (i > 0) {
            //删除角色权限关系
            roleAuthMapper.removeRoleAuthByRid(roleId);
            return Result.ok("角色删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "角色删除失败！");
    }

    //查询角色分配的所有权限菜单
    @Override
    public List<Integer> queryRoleAuthIds(Integer roleId) {
        return roleAuthMapper.findAuthIdByRid(roleId);
    }


    //给角色分配权限
    @Transactional//事务处理
    @Override
    public void saveRoleAuth(AssignAuthDto assignAuthDto) {

        //删除角色之前分配的所有权限
        roleAuthMapper.removeRoleAuthByRid(assignAuthDto.getRoleId());

        //添加角色权限关系
        List<Integer> authIdList = assignAuthDto.getAuthIds();

        for (Integer authId : authIdList) {
            RoleAuth roleAuth = new RoleAuth();
            roleAuth.setRoleId(assignAuthDto.getRoleId());
            roleAuth.setAuthId(authId);
            roleAuthMapper.insertRoleAuth(roleAuth);
        }

    }


    //修改角色
    @CacheEvict(key = "'all:role'")//清理redis缓存
    @Override
    public Result setRoleByRid(Role role) {
        int i = roleMapper.setDescByRid(role);
        if (i > 0) {
            return Result.ok("角色修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "角色修改失败！");
    }


}
