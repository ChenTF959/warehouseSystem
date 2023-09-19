package com.ctf.mapper;

import com.ctf.entity.Unit;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 21:15
 * @description:
 */
public interface UnitMapper {

    //查询所有单位的方法
    public List<Unit> findAllUnit();
}
