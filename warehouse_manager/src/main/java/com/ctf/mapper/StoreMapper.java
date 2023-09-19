package com.ctf.mapper;

import com.ctf.entity.Store;
import com.ctf.page.Page;
import com.ctf.vo.StoreCountVo;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 16:31
 * @description:
 */
public interface StoreMapper {

    //查询所有仓库的方法
    public List<Store> findAllStore();

    //查询每个仓库的商品数量
    public List<StoreCountVo> findStoreCount();

    //查询仓库总行数的方法
    public int selectStoreCount(Store store);

    //分页查询仓库的方法
    public List<Store> selectStorePage(@Param("page") Page page, @Param("store") Store store);

    //根据仓库编号查询仓库的方法
    public Store selectStoreByNum(String storeNum);

    //添加仓库的方法
    public int insertStore(Store store);

    //根据仓库id修改仓库的方法
    public int updateStoreById(Store store);

    //根据仓库id删除仓库的方法
    public int deleteStoreById(Integer storeId);



}
