package com.ctf.controller;

import com.ctf.entity.ProductType;
import com.ctf.entity.Result;
import com.ctf.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/19 14:35
 * @description:
 */

@RequestMapping("/productCategory")
@RestController
public class ProductTypeController {

    //注入ProductTypeService
    @Autowired
    private ProductTypeService productTypeService;

    /**
     * 查询商品分类树的url接口/productCategory/product-category-tree
     */
    @RequestMapping("/product-category-tree")
    public Result productCategoryTree() {
        //执行业务
        List<ProductType> typeTreeList = productTypeService.allProductTypeTree();
        //响应
        return Result.ok(typeTreeList);
    }

    /**
     * 校验分类编码是否已存在的url接口/productCategory/verify-type-code
     */
    @RequestMapping("/verify-type-code")
    public Result checkTypeCode(String typeCode) {
        //执行业务
        Result result = productTypeService.checkTypeCode(typeCode);
        //响应
        return result;
    }

    /**
     * 添加商品分类的url接口/productCategory/type-add
     *
     * @RequestBody ProductType productType将请求传递的json数据封装到参数ProductType对象;
     */
    @RequestMapping("/type-add")
    public Result addProductType(@RequestBody ProductType productType) {
        //执行业务
        Result result = productTypeService.saveProductType(productType);
        //响应
        return result;
    }

    /**
     * 删除商品分类的url接口/productCategory/type-delete/{typeId}
     *
     * @PathVariable Integer typeId将路径占位符typeId的值赋值给参数变量typeId;
     */
    @RequestMapping("/type-delete/{typeId}")
    public Result deleteType(@PathVariable Integer typeId) {
        //执行业务
        Result result = productTypeService.deleteProductType(typeId);
        //响应
        return result;
    }

    /**
     * 修改商品分类的url接口/productCategory/type-update
     *
     * @RequestBody ProductType productType将请求传递的json数据封装到参数ProductType对象;
     */
    @RequestMapping("/type-update")
    public Result updateType(@RequestBody ProductType productType) {
        //执行业务
        Result result = productTypeService.setProductType(productType);
        //响应
        return result;
    }
}
