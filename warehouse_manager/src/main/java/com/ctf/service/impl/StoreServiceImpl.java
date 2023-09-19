package com.ctf.service.impl;

import com.ctf.entity.Result;
import com.ctf.entity.Store;
import com.ctf.mapper.StoreMapper;
import com.ctf.page.Page;
import com.ctf.service.StoreService;
import com.ctf.vo.StoreCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 16:34
 * @description:
 */


//指定缓存的名称即键的前缀,一般是@CacheConfig标注的类的全类名
@CacheConfig(cacheNames = "com.ctf.service.impl.StoreServiceImpl")
@Service
public class StoreServiceImpl implements StoreService {

    //注入StoreMapper
    @Autowired
    private StoreMapper storeMapper;

    /*
      查询所有仓库的业务方法
     */
    //对查询到的所有仓库进行缓存,缓存到redis的键为all:store
    @Cacheable(key = "'all:store'")
    @Override
    public List<Store> queryAllStore() {
        //查询所有仓库
        return storeMapper.findAllStore();
    }


    //查询每个仓库商品数量的业务方法
    @Override
    public List<StoreCountVo> queryStoreCount() {
        return storeMapper.findStoreCount();
    }

    //分页查询仓库的业务方法
    @Override
    public Page queryStorePage(Page page, Store store) {

        //查询仓库总行数
        int storeCount = storeMapper.selectStoreCount(store);

        //分页查询仓库
        List<Store> storeList = storeMapper.selectStorePage(page, store);

        //将查到的总行数和当前页数据封装到Page对象
        page.setTotalNum(storeCount);
        page.setResultList(storeList);

        return page;
    }

    //校验仓库编号是否已存在的业务方法
    @Override
    public Result checkStoreNum(String storeNum) {
        //根据仓库编号查询仓库
        Store store = storeMapper.selectStoreByNum(storeNum);
        return Result.ok(store==null);
    }

    //添加仓库的业务方法
    @Override
    public Result saveStore(Store store) {
        //添加仓库
        int i = storeMapper.insertStore(store);
        if(i>0){
            return Result.ok("仓库添加成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "仓库添加失败！");
    }

    //修改仓库的业务方法
    @Override
    public Result updateStore(Store store) {
        //根据仓库id修改仓库
        int i = storeMapper.updateStoreById(store);
        if(i>0){
            return Result.ok("仓库修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "仓库修改失败！");
    }

    //删除仓库的业务方法
    @Override
    public Result deleteStore(Integer storeId) {
        //根据仓库id删除仓库
        int i = storeMapper.deleteStoreById(storeId);
        if(i>0){
            return Result.ok("仓库删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "仓库删除失败！");
    }


}
