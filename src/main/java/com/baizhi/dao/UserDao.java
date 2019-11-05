package com.baizhi.dao;

import com.baizhi.entity.MonthAndCount;
import com.baizhi.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {

    List<MonthAndCount> selectSexAadMonth(String sex);
}
