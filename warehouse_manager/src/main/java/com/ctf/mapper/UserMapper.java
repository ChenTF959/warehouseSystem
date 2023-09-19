package com.ctf.mapper;

import com.ctf.entity.User;
import com.ctf.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Author: ChenTF
 * @Date: 2023/9/12 21:09
 * @description:user_info的mapper接口
 */

public interface UserMapper {

    //根据账号查询用户信息的方法
    public User findUserByCode(String userCode);


    //查询用户行数
    public Integer findUserRowCount(User user);


    //分页查询用户的方法
    public List<User> findUserByPage(@Param("page") Page page, @Param("user") User user);


    //添加用户的方法
    public int insertUser(User user);

    //根据用户id修改用户状态的方法
    public int setStateByUid(Integer userId, String userState);

    //根据用户ids修改用户为删除状态方法
    public int setDeleteByUids(List<Integer> userIdList);

    //根据用户id修改用户昵称的方法
    public int setUserNameByUid(User user);

    //根据用户id修改密码
    public int setPasswordByUid(Integer userId, String password);


}