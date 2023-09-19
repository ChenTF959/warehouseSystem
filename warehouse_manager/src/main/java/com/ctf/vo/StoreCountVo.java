package com.ctf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ChenTF
 * @Date: 2023/9/19 19:44
 * @description:封装每个仓库商品数量的vo类，前端使用echarts框架，，后台负责查询组装数据，然后将后台查询到的数据响应给前端，
 * 前端再以echarts框架所需的结构，格式填充到图形中
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreCountVo {

    private Integer storeId; //出库id

    private String storeName; //仓库名称

    private Integer totalInvent; //仓库的商品数量
}
