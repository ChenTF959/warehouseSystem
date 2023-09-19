package com.ctf.controller;

import com.ctf.entity.Result;
import com.ctf.service.StoreService;
import com.ctf.vo.StoreCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/19 20:16
 * @description:
 */

@RequestMapping("/statistics")
@RestController
public class StatisticsController {

    @Autowired
    private StoreService storeService;

    //统计仓库库存的url接口/statistics/store-invent
    @RequestMapping("/store-invent")
    public Result storeInvent() {
        List<StoreCountVo> storeCountVoList = storeService.queryStoreCount();
        return Result.ok(storeCountVoList);
    }
}
