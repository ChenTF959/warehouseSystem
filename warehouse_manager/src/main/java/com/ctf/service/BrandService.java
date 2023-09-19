package com.ctf.service;

import com.ctf.entity.Brand;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 16:48
 * @description:
 */
public interface BrandService {
    //查询所有品牌的业务方法
    public List<Brand> queryAllBrand();
}
