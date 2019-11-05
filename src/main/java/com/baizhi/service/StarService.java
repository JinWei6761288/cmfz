package com.baizhi.service;

import com.baizhi.entity.Star;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface StarService {
    Map<String,Object> findAll(Integer page,Integer rows);
    String add(Star star);
    String update(Star star,HttpServletRequest request);
    void delete(String id, HttpServletRequest request);

    Map<String,Object> finAllName();
}
