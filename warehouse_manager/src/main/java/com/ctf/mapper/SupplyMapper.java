package com.ctf.mapper;

import com.ctf.entity.Supply;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 20:55
 * @description:
 */
public interface SupplyMapper {

    //查询所有供应商的方法
    public List<Supply> findAllSupply();
}
