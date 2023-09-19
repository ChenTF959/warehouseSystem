package com.ctf.service;

import com.ctf.entity.Result;
import com.ctf.entity.Store;
import com.ctf.page.Page;
import com.ctf.vo.StoreCountVo;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 16:33
 * @description:
 */
public interface StoreService {

    //查询所有仓库
    public List<Store> queryAllStore();


    //查询每个仓库商品数量的业务方法
    public List<StoreCountVo> queryStoreCount();

    //分页查询仓库的业务方法
    public Page queryStorePage(Page page, Store store);

    //校验仓库编号是否已存在的业务方法
    public Result checkStoreNum(String storeNum);

    //添加仓库的业务方法
    public Result saveStore(Store store);

    //修改仓库的业务方法
    public Result updateStore(Store store);

    //删除仓库的业务方法
    public Result deleteStore(Integer storeId);
}
