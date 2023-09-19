package com.ctf.service;

import com.ctf.entity.ProductType;
import com.ctf.entity.Result;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 17:34
 * @description:
 */


public interface ProductTypeService {

    //查询所有商品分类树
    public List<ProductType> allProductTypeTree();

    //校验分类编码\是否已存在的业务方法
    public Result checkTypeCode(String typeCode);

    //添加商品分类的业务方法
    public Result saveProductType(ProductType productType);

    //删除商品分类的业务方法
    public Result deleteProductType(Integer typeId);

    //修改商品分类的业务方法
    public Result setProductType(ProductType productType);
}
