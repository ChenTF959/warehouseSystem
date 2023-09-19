package com.ctf.mapper;

import com.ctf.entity.Product;
import com.ctf.page.Page;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 16:58
 * @description:
 */
public interface ProductMapper {

    //查询商品总行数
    public int selectProductCount(Product product);

    //分页查询商品
    public List<Product> selectProductPage(@Param("page") Page page, @Param("product") Product product);

    //根据型号查询商品
    public Product findProductByNum(String productNum);

    //添加商品
    public int insertProduct(Product product);

    //根据商品id修改商品的上下架状态
    public int setStateByPid(Integer productId, String upDownState);

    //根据商品id删除商品的方法
    public int removeProductByIds(List<Integer> productIdList);


    //根据商品id修改商品的方法
    public int setProductById(Product product);

    //根据商品id修改商品库存的方法
    public int setInventById(Integer productId, Integer invent);

    //根据商品id查出商品库存的方法
    public int findInventById(Integer productId);
}
