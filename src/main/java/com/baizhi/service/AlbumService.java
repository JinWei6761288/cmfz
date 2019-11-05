package com.baizhi.service;

import com.baizhi.entity.Album;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AlbumService {
    Map<String,Object> findAll(Integer page, Integer rows);
    String add(Album album);
    String update(Album album, HttpServletRequest request);
    void delete(String id,HttpServletRequest request);

    Album selectOne(String id);
}
