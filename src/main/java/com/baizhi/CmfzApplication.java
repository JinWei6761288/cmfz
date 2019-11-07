package com.baizhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.baizhi.dao")
@org.mybatis.spring.annotation.MapperScan("com.baizhi.dao")//用来扫描项目中所有DAO接口
public class CmfzApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmfzApplication.class, args);
    }
    @Bean
    public Jedis getJedis(){
        return new Jedis("192.168.136.143",6379);
    }
}
