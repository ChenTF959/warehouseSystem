package com.ctf.service;

import com.ctf.entity.Place;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 21:11
 * @description:
 */
public interface PlaceService {

    //查询所有产地的业务方法
    public List<Place> queryAllPlace();
}
