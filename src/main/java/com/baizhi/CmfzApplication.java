package com.baizhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.baizhi.dao")
@org.mybatis.spring.annotation.MapperScan("com.baizhi.dao")//用来扫描项目中所有DAO接口
public class CmfzApplication {

    public static void main(String[] args) {
        System.out.println("进入启动类");
        System.out.println("---------------");
        System.out.println("---------------");
        System.out.println("---------------");
        SpringApplication.run(CmfzApplication.class, args);
    }

}
