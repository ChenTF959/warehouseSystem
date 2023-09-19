package com.ctf.mapper;

import com.ctf.entity.Purchase;
import com.ctf.page.Page;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/19 14:08
 * @description:
 */
public interface PurchaseMapper {

    //添加采购单的方法
    public int insertPurchase(Purchase purchase);

    //查询采购单总行数的方法
    public int findPurchaseCount(Purchase purchase);

    //分页查询采购单的方法
    public List<Purchase> findPurchasePage(@Param("page") Page page, @Param("purchase") Purchase purchase);


    //根据id删除采购单的方法
    public int removePurchaseById(Integer buyId);



    //根据id修改预计采购数量和实际采购数量的方法
    public int setNumById(Purchase purchase);


    //根据id将采购单状态改为已入库的方法
    public int setIsInById(Integer buyId);
}
