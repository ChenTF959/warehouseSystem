package com.ctf.mapper;

import com.ctf.entity.Brand;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 16:44
 * @description:
 */
public interface BrandMapper {

    //查询所有品牌的方法
    public List<Brand> findAllBrand();
}
