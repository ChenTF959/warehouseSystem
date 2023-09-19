package com.ctf.mapper;

import com.ctf.entity.Place;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 21:08
 * @description:
 */
public interface PlaceMapper {

    //查询所有产地
    public List<Place> findAllPlace();
}
