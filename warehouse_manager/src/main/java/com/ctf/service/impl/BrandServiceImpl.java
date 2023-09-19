package com.ctf.service.impl;

import com.ctf.entity.Brand;
import com.ctf.mapper.BrandMapper;
import com.ctf.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 16:49
 * @description:
 */

//指定缓存的名称即键的前缀,一般是@CacheConfig标注的类的全类名
@CacheConfig(cacheNames = "com.ctf.service.impl.BrandServiceImpl")
@Service
public class BrandServiceImpl implements BrandService {

    //注入BrandMapper
    @Autowired
    private BrandMapper brandMapper;

    //查询所有品牌的业务方法
    @Cacheable(key = "'all:brand'")//对查询到的所有品牌进行缓存,缓存到redis的键为all:brand
    @Override
    public List<Brand> queryAllBrand() {
        //查询所有品牌
        return brandMapper.findAllBrand();
    }
}
