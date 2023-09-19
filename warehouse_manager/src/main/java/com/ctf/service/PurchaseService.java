package com.ctf.service;

import com.ctf.entity.Purchase;
import com.ctf.entity.Result;
import com.ctf.page.Page;

/**
 * @Author: ChenTF
 * @Date: 2023/9/19 14:06
 * @description:
 */
public interface PurchaseService {

    //添加采购单的业务方法
    public Result savePurchase(Purchase purchase);

    //分页查询采购单的业务方法
    public Page queryPurchasePage(Page page, Purchase purchase);

    //删除采购单的业务方法
    public Result deletePurchaseById(Integer buyId);


    //修改采购单的业务方法
    public Result updatePurchaseById(Purchase purchase);

}
