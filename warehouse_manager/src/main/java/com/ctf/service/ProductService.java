package com.ctf.service;

import com.ctf.entity.Product;
import com.ctf.entity.Result;
import com.ctf.page.Page;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 17:15
 * @description:
 */
public interface ProductService {

    //分页查询商品
    public Page queryProductPage(Page page, Product product);

    //添加商品的业务方法
    public Result saveProduct(Product product);

    //修改商品上下架状态的业务方法
    public Result updateStateByPid(Product product);

    //删除商品的业务方法
    public Result deleteProductByIds(List<Integer> productList);


    //修改商品的业务方法
    public Result setProductById(Product product);

}
