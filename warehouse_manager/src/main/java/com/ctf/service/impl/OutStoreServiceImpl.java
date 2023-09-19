package com.ctf.service.impl;

import com.ctf.entity.OutStore;
import com.ctf.entity.Product;
import com.ctf.entity.Result;
import com.ctf.mapper.OutStoreMapper;
import com.ctf.mapper.ProductMapper;
import com.ctf.page.Page;
import com.ctf.service.OutStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/19 14:22
 * @description:
 */
@Service
public class OutStoreServiceImpl implements OutStoreService {

    //注入OutStoreMapper
    @Autowired
    private OutStoreMapper outStoreMapper;

    //注入ProductMapper
    @Autowired
    private ProductMapper productMapper;

    //添加出库单的业务方法
    @Override
    public Result saveOutStore(OutStore outStore) {
        //添加出库单
        int i = outStoreMapper.insertOutStore(outStore);
        if (i > 0) {
            return Result.ok("添加出库单成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "添加出库单失败！");
    }

    //分页查询出库单的业务方法
    @Override
    public Page queryOutStorePage(Page page, OutStore outStore) {

        //查询出库单总行数
        int count = outStoreMapper.findOutStoreCount(outStore);

        //分页查询出库单
        List<OutStore> outStoreList = outStoreMapper.findOutStorePage(page, outStore);

        //将查询到的总行数和当前页数据封装到Page对象
        page.setTotalNum(count);
        page.setResultList(outStoreList);

        return page;
    }

    //确定出库的业务方法
    @Transactional//事务处理
    @Override
    public Result outStoreConfirm(OutStore outStore) {

        //判断商品库存是否充足
        int invent = productMapper.findInventById(outStore.getProductId());//商品现有库存
        if (invent < outStore.getOutNum()) {
            return Result.err(Result.CODE_ERR_BUSINESS, "库存不足");
        }
        //修改出库单状态
        int i = outStoreMapper.setIsOutById(outStore.getOutsId());
        if (i > 0) {
            int j = productMapper.setInventById(outStore.getProductId(), -outStore.getOutNum());
            if (j > 0) {
                return Result.ok("出库成功！");
            }
            return Result.err(Result.CODE_ERR_BUSINESS, "出库失败！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "出库失败！");
    }
}
