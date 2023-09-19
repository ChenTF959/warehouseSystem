package com.ctf.service.impl;

import com.alibaba.fastjson.JSON;
import com.ctf.entity.Auth;
import com.ctf.mapper.AuthMapper;
import com.ctf.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/13 11:07
 * @description:
 */

//指定缓存的名称(数据保存到redis中键的前缀,一般值给标注@CacheConfig注解类的全路径)
@CacheConfig(cacheNames = "com.ctf.service.impl.AuthServiceImpl")
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    //查询用户菜单树
    //向redis缓存用户菜单树 -- 键authTree:userId 值:List<Auth>转的json串
    @Override
    public List<Auth> authTreeByUid(Integer userId) {

        //先从redis查询缓存的用户菜单树
        String authTreeJson = redisTemplate.opsForValue().get("authTree:" + userId);
        if (StringUtils.hasText(authTreeJson)) {//说明redis中有用户菜单的缓存
            //将菜单树List<Auth>转的json串转回菜单树List<Auth>并返回
            List<Auth> authTreeList = JSON.parseArray(authTreeJson, Auth.class);
            return authTreeList;
        }
        //没有用户菜单树缓存
        //查询用户权限下的所有菜单
        List<Auth> allAuthList = authMapper.findAuthByUid(userId);
        //将所有菜单List<Auth>转成菜单树List<Auth>
        List<Auth> authTreeList = allAuthToAuthTree(allAuthList, 0);
        //向redis中缓存菜单树List<Auth>
        redisTemplate.opsForValue().set("authTree:" + userId, JSON.toJSONString(authTreeList));

        return authTreeList;
    }

    //将所有菜单List<Auth>转成菜单树List<Auth>的递归算法
    private List<Auth> allAuthToAuthTree(List<Auth> allAuthList, Integer pid) {
        //查询出所有一级菜单
        List<Auth> firstLevelAuthList = new ArrayList<>();
        for (Auth auth : allAuthList) {
            if (auth.getParentId() == pid) {
                firstLevelAuthList.add(auth);
            }
        }

        //拿到每个一级菜单的二级菜单
        for (Auth firstAuth : firstLevelAuthList) {
            List<Auth> secondLevelAuthList = allAuthToAuthTree(allAuthList, firstAuth.getAuthId());
            firstAuth.setChildAuth(secondLevelAuthList);
        }
        return firstLevelAuthList;
    }


    //查询所有权限菜单树
    @Cacheable(key = "'all:authTree'")
    @Override
    public List<Auth> allAuthTree() {

        //查出所有权限菜单
        List<Auth> allAuthList = authMapper.findAllAuth();
        //将所有权限菜单List<Auth>转成权限菜单树List<Auth>
        List<Auth> authTreeList = allAuthToAuthTree(allAuthList, 0);
        return authTreeList;
    }



}
