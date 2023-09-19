package com.ctf.service.impl;

import com.ctf.entity.Product;
import com.ctf.entity.Result;
import com.ctf.mapper.ProductMapper;
import com.ctf.page.Page;
import com.ctf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 17:16
 * @description:
 */

@Service
public class ProductServiceImpl implements ProductService {

    //注入ProductMapper
    @Autowired
    private ProductMapper productMapper;

    //分页查询商品
    @Override
    public Page queryProductPage(Page page, Product product) {

        //查询商品总行数
        int productCount = productMapper.selectProductCount(product);

        //分页查询商品
        List<Product> productList = productMapper.selectProductPage(page, product);

        //将查询到的总行数和当前页数据组装到Page对象
        page.setTotalNum(productCount);
        page.setResultList(productList);
        return page;
    }

    /*
      将配置文件的file.access-path属性值注入给service的accessPath属性,
     * 其为上传的图片的访问地址的目录路径/img/upload/;
     */
    @Value("${file.access-path}")
    private String fileAccessPath;

    //添加商品的业务方法
    @Override
    public Result saveProduct(Product product) {

        //判断商品的型号是否已存在
        Product prct = productMapper.findProductByNum(product.getProductNum());
        if (prct != null) { //商品已存在
            return Result.err(Result.CODE_ERR_BUSINESS, "商品型号已存在！");
        }

        //处理上传的图片的访问地址 -- /img/upload/图片名称
        product.setImgs(fileAccessPath +product.getImgs());

        //添加商品
        int i = productMapper.insertProduct(product);

        if(i>0){
            return Result.ok("添加商品成功！");
        }

        return Result.err(Result.CODE_ERR_BUSINESS, "添加商品失败！");
    }

    //修改商品上下架状态的业务方法
    @Override
    public Result updateStateByPid(Product product) {
        //根据商品id修改商品上下架状态
        int i = productMapper.setStateByPid(product.getProductId(), product.getUpDownState());
        if(i>0){
            return Result.ok("商品上下架状态修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "商品上下架状态修改失败！");
    }

    //删除商品的业务方法
    @Override
    public Result deleteProductByIds(List<Integer> productList) {
        int i = productMapper.removeProductByIds(productList);
        if (i > 0) { //删除成功
            return Result.ok("商品删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "商品删除失败！");
    }

    //修改商品的业务方法
    @Override
    public Result setProductById(Product product) {
        //判断修改后的型号是否存在
        Product prod = productMapper.findProductByNum(product.getProductNum());
        if (prod != null && !prod.getProductId().equals(product.getProductId())) { //商品型号修改，且修改后的型号已存在
            return Result.err(Result.CODE_ERR_BUSINESS, "修改后的型号已存在");
        }

        //判断上传的图片是否被修好，如果修改了处理访问路径
        /*
          处理商品上传的图片的访问地址:
          如果product对象的imgs属性值没有以/img/upload/开始,说明商品的图片
          被修改了即上传了新的图片,那么product对象的imgs属性值只是图片的名称,
          则给图片名称前拼接/img/upload构成商品新上传的图片的访问地址;
         */
        if(!product.getImgs().contains(fileAccessPath)){
            product.setImgs(fileAccessPath+product.getImgs());
        }
        //根据商品id修改商品信息
        int i = productMapper.setProductById(product);
        if(i>0){
            return Result.ok("商品修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"商品修改失败！");
    }
}
