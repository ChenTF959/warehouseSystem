package com.ctf.mapper;

import com.ctf.entity.InStore;
import com.ctf.page.Page;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/19 17:05
 * @description:
 */
public interface InStoreMapper {

    //添加入库单的方法
    public int insertInStore(InStore inStore);

    //查询入库单行数的方法
    public Integer findInStoreCount(InStore inStore);

    //分页查询入库单的方法
    public List<InStore> findInStorePage(@Param("page") Page page, @Param("inStore") InStore inStore);

    //根据id将入库单状态改为已入库的方法
    public int setIsInById(Integer inStoreId);
}
