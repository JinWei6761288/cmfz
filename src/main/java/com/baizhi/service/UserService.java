package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    Map<String,Object> selectUsersByStarId(Integer page, Integer rows, String starId);//明星模块查询所有用户
    Map<String,Object> findAll(Integer page, Integer rows);//用户模块查询所有用户
    void update(User user);
    List<User> selectAll();

    List<Integer> selectSexAadMonth(String sex);//注册趋势

}
