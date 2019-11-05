package com.baizhi.entity;

import com.baizhi.dao.AdminDao;
import io.goeasy.GoEasy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CmfzApplicationTests {
    @Autowired
    private AdminDao adminDao;
@Test
    void pub(){
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io","BC-73401fae92ae4377ad88afbd19a96afb");
        goEasy.publish("my_channel","Hello GoEasy!");
    }

    @Test
    void contextLoads() {
        //List<Admin> list = adminDao.selectAll();
        //list.forEach(admins -> System.out.println(admins));
        /*Admin admin = new Admin();
        RowBounds rowBounds = new RowBounds(0,2);
        List<Admin> list = adminDao.selectByRowBounds(admin, rowBounds);
        list.forEach(admins -> System.out.println(admins));*/
        System.out.println("阿斯顿");
    }

}
