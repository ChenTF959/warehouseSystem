package com.ctf.mapper;

import com.ctf.entity.ProductType;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 17:32
 * @description:
 */
public interface ProductTypeMapper {

    //查询所有商品分类
    public List<ProductType> findAllProductType();

    //根据分类编码查询商品分类的方法
    public ProductType findTypeByCodeOrName(ProductType productType);

    //添加商品分类的方法
    public int insertProductType(ProductType productType);

    //根据分类id删除分类及其所有子级分类的方法
    public int removeProductType(Integer typeId);

    //根据分类id修改分类的方法
    public int setProductTypeById(ProductType productType);
}
