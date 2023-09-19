package com.ctf.mapper;

import com.ctf.entity.OutStore;
import com.ctf.page.Page;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/19 14:23
 * @description:
 */
public interface OutStoreMapper {


    //添加出库单的方法
    public int insertOutStore(OutStore outStore);

    //查询出库单总行数的方法
    public Integer findOutStoreCount(OutStore outStore);

    //分页查询出库单的方法
    public List<OutStore> findOutStorePage(@Param("page") Page page, @Param("outStore") OutStore outStore);

    //根据id将出库单状态改为已出库的方法
    public int setIsOutById(Integer outStoreId);
}
