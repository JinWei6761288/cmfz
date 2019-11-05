package com.baizhi.service;

import com.baizhi.entity.Banner;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BannerService {
    List<Banner> findAll(Integer page, Integer rows);
    int findCount(Banner banner);

    String save(Banner banner);

    String update(Banner banner,HttpServletRequest request);

    void delete(String id, HttpServletRequest request);

}
