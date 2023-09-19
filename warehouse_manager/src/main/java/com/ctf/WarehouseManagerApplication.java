package com.ctf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


//1 开启redis注解版缓存k
@EnableCaching
//mapper接口扫描器，指明mapper接口所在包，然后就会自动为mapper接口创建代理对象并加入到IOC容器
@MapperScan(basePackages = {"com.ctf.mapper"})
@SpringBootApplication
public class WarehouseManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseManagerApplication.class, args);
    }
}
