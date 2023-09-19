package com.ctf.service;

import com.ctf.entity.Supply;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 21:04
 * @description:
 */
public interface SupplyService {

    //查询所有供应商的业务方法
    public List<Supply> queryAllSupply();
}
