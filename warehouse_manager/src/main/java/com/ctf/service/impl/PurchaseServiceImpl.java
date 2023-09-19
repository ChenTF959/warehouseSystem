package com.ctf.service.impl;

import com.ctf.entity.Purchase;
import com.ctf.entity.Result;
import com.ctf.mapper.PurchaseMapper;
import com.ctf.page.Page;
import com.ctf.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/19 14:12
 * @description:
 */


@Service
public class PurchaseServiceImpl implements PurchaseService {

    //注入PurchaseMapper
    @Autowired
    private PurchaseMapper purchaseMapper;

    //添加采购单的业务方法
    @Override
    public Result savePurchase(Purchase purchase) {

        int i = purchaseMapper.insertPurchase(purchase);
        if(i>0){
            return Result.ok("采购单添加成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "采购单添加失败！");
    }

    //分页查询采购单的业务方法
    @Override
    public Page queryPurchasePage(Page page, Purchase purchase) {

        //查询采购单总行数
        Integer count = purchaseMapper.findPurchaseCount(purchase);

        //分页查询采购单
        List<Purchase> purchaseList = purchaseMapper.findPurchasePage(page, purchase);

        //组装分页信息
        page.setTotalNum(count);
        page.setResultList(purchaseList);

        return page;
    }


    //删除采购单的业务方法
    @Override
    public Result deletePurchaseById(Integer buyId) {
        //根据id删除采购单
        int i = purchaseMapper.removePurchaseById(buyId);
        if(i>0){
            return Result.ok("采购单删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "采购单删除失败！");
    }

    //修改采购单的业务方法
    @Override
    public Result updatePurchaseById(Purchase purchase) {
        //根据id修改采购单
        int i = purchaseMapper.setNumById(purchase);
        if(i>0){
            return Result.ok("采购单修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "采购单修改失败！");
    }

}
