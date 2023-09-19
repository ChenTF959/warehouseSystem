package com.ctf.service;

import com.ctf.entity.OutStore;
import com.ctf.entity.Result;
import com.ctf.page.Page;

/**
 * @Author: ChenTF
 * @Date: 2023/9/19 14:22
 * @description:
 */
public interface OutStoreService {

    //添加出库单的业务方法
    public Result saveOutStore(OutStore outStore);

    //分页查询出库单的业务方法
    public Page queryOutStorePage(Page page, OutStore outStore);

    //确定出库的业务方法
    public Result outStoreConfirm(OutStore outStore);
}
