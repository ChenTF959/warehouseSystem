package com.ctf.service;

import com.ctf.entity.Unit;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 21:18
 * @description:
 */
public interface UnitService {

    //查询所有单位的业务方法
    public List<Unit> queryAllUnit();
}
