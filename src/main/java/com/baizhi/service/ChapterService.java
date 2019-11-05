package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    Map<String,Object> selectChapterByBlbumId(Integer page, Integer rows, String albumId);//专辑模块查询章节

    String add(Chapter chapter);

    void edit(Chapter chapter);
}
