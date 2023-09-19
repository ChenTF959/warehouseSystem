package com.ctf.service.impl;

import com.ctf.entity.Unit;
import com.ctf.mapper.UnitMapper;
import com.ctf.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 21:20
 * @description:
 */


//指定缓存的名称即键的前缀,一般是@CacheConfig标注的类的全类名
@CacheConfig(cacheNames = "com.ctf.service.impl.UnitServiceImpl")
@Service
public class UnitServiceImpl implements UnitService {

    //注入UnitMapper
    @Autowired
    private UnitMapper unitMapper;

    /*
      查询所有单位的业务方法
     */
    //对查询到的所有单位进行缓存,缓存到redis的键为all:unit
    @Cacheable(key = "'all:unit'")
    @Override
    public List<Unit> queryAllUnit() {
        //查询所有单位
        return unitMapper.findAllUnit();
    }

}
