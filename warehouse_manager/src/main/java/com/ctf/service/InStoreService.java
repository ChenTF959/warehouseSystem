package com.ctf.service;

import com.ctf.entity.InStore;
import com.ctf.entity.Result;
import com.ctf.page.Page;

/**
 * @Author: ChenTF
 * @Date: 2023/9/19 17:06
 * @description:
 */
public interface InStoreService {

    //添加入库单的业务方法
    public Result saveInStore(InStore inStore, Integer buyId);

    //分页查询入库单的业务方法
    public Page queryInStorePage(Page page, InStore inStore);

    //确定入库的业务方法
    public Result inStoreConfirm(InStore inStore);
}
