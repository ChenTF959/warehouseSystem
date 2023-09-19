package com.ctf.service.impl;

import com.ctf.entity.InStore;
import com.ctf.entity.Result;
import com.ctf.mapper.InStoreMapper;
import com.ctf.mapper.ProductMapper;
import com.ctf.mapper.PurchaseMapper;
import com.ctf.page.Page;
import com.ctf.service.InStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/19 17:07
 * @description:
 */

@Service
public class InStoreServiceImpl implements InStoreService {

    //注入InStoreMapper
    @Autowired
    private InStoreMapper inStoreMapper;

    //注入PurchaseMapper
    @Autowired
    private PurchaseMapper purchaseMapper;

    //注入ProductMapper
    @Autowired
    private ProductMapper productMapper;

    //添加入库单的业务方法
    @Transactional//事务处理
    @Override
    public Result saveInStore(InStore inStore, Integer buyId) {
        //添加入库单
        int i = inStoreMapper.insertInStore(inStore);
        if (i > 0) {
            //根据id将采购单状态改为已入库
            int j = purchaseMapper.setIsInById(buyId);
            if (j > 0) {
                return Result.ok("入库单添加成功！");
            }
            return Result.err(Result.CODE_ERR_BUSINESS, "入库单添加失败！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "入库单添加失败！");
    }

    //分页查询入库单的业务方法
    @Override
    public Page queryInStorePage(Page page, InStore inStore) {

        //查询入库单行数
        int inStoreCount = inStoreMapper.findInStoreCount(inStore);

        //分页查询入库单
        List<InStore> inStoreList = inStoreMapper.findInStorePage(page, inStore);

        //将查询到的总行数和当前页数据封装到Page对象
        page.setTotalNum(inStoreCount);
        page.setResultList(inStoreList);

        return page;
    }

    //确定入库的业务方法
    @Transactional//事务处理
    @Override
    public Result inStoreConfirm(InStore inStore) {

        //根据id将入库单状态改为已入库
        int i = inStoreMapper.setIsInById(inStore.getInsId());
        if (i > 0) {
            //根据商品id增加商品库存
            int j = productMapper.setInventById(inStore.getProductId(), inStore.getInNum());
            if (j > 0) {
                return Result.ok("入库成功！");
            }
            return Result.err(Result.CODE_ERR_BUSINESS, "入库失败！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "入库失败！");
    }
}
