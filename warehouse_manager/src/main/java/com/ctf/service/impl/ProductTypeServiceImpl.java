package com.ctf.service.impl;

import com.ctf.entity.ProductType;
import com.ctf.entity.Result;
import com.ctf.mapper.ProductTypeMapper;
import com.ctf.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 17:35
 * @description:
 */

@CacheConfig(cacheNames = "com.ctf.service.impl.productTypeServiceImpl")
@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    //注入ProductTypeMapper
    @Autowired
    private ProductTypeMapper productTypeMapper;

    /*
      查询所有商品分类树的业务方法
     */
    //对查询到的所有商品分类树进行缓存,缓存到redis的键为all:typeTree
    @Cacheable(key = "'all:typeTree'")
    @Override
    public List<ProductType> allProductTypeTree() {
        //查询所有商品分类
        List<ProductType> allProductTypeList = productTypeMapper.findAllProductType();
        //将所有商品分类List<ProductType>转成商品分类树List<ProductType>
        List<ProductType> typeTreeList = allTypeToTypeTree(allProductTypeList, 0);
        //返回商品分类树List<ProductType>
        return typeTreeList;
    }

    //将查询到的所有商品分类List<ProductType>转成商品分类树List<ProductType>的算法
    private List<ProductType> allTypeToTypeTree(List<ProductType> allTypeList, Integer parentId){

        List<ProductType> firstLevelType = new ArrayList<>();
        for (ProductType productType : allTypeList) {
            if(productType.getParentId().equals(parentId)){
                firstLevelType.add(productType);
            }
        }

        for (ProductType productType : firstLevelType) {
            List<ProductType> secondLevelType = allTypeToTypeTree(allTypeList, productType.getTypeId());
            productType.setChildProductCategory(secondLevelType);
        }
        return firstLevelType;
    }

    //校验分类编码是否已存在的业务方法
    @Override
    public Result checkTypeCode(String typeCode) {

        //根据分类编码查询商品分类
        ProductType productType = new ProductType();
        productType.setTypeCode(typeCode);
        ProductType prodType = productTypeMapper.findTypeByCodeOrName(productType);
        return Result.ok(prodType == null);
    }

    /*
      添加商品分类的业务方法
      @CacheEvict(key = "'all:typeTree'")清除所有商品分类树的缓存;
     */
    @CacheEvict(key = "'all:typeTree'")
    @Override
    public Result saveProductType(ProductType productType) {

        //校验分类名称是否已存在
        ProductType prodType = new ProductType();
        prodType.setTypeName(productType.getTypeName());
        ProductType prodCategory = productTypeMapper.findTypeByCodeOrName(prodType);

        if(prodCategory != null){
            return Result.err(Result.CODE_ERR_BUSINESS, "分类名称已存在！");
        }
        //添加分类
        int i = productTypeMapper.insertProductType(productType);
        if (i > 0) {
            return Result.ok("分类添加成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "分类添加失败！");
    }

    /*
      删除商品分类的业务方法

      @CacheEvict(key = "'all:typeTree'")清除所有商品分类树的缓存;
     */
    @CacheEvict(key = "'all:typeTree'")
    @Override
    public Result deleteProductType(Integer typeId) {
        //根据分类id删除分类及其所有子级分类
        int i = productTypeMapper.removeProductType(typeId);
        if(i > 0){
            return Result.ok("分类删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "分类删除失败！");
    }

    /*
      修改商品分类的业务方法

      @CacheEvict(key = "'all:typeTree'")清除所有商品分类树的缓存;
     */
    @CacheEvict(key = "'all:typeTree'")
    @Override
    public Result setProductType(ProductType productType) {

        //检测修改的分类名称是否已存在
        ProductType prodType = new ProductType();
        prodType.setTypeName(productType.getTypeName());
        ProductType prodCategory = productTypeMapper.findTypeByCodeOrName(prodType);
        if (prodCategory != null && prodCategory.getTypeId().equals(productType.getTypeId())) {
            return Result.err(Result.CODE_ERR_BUSINESS, "商品分类名称已存在");
        }

        //根据分类id修改分类
        int i = productTypeMapper.setProductTypeById(productType);
        if(i > 0){
            return Result.ok("分类修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "分类修改失败！");
    }
}