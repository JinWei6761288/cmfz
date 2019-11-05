package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private ChapterDao chapterDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Album album = new Album();
        Chapter chapter = new Chapter();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Album> list = albumDao.selectByRowBounds(album, rowBounds);
        for (Album album1 : list) {
            String id = album1.getId();
            chapter.setAlbumId(id);
            int chapterCount = chapterDao.selectCount(chapter);
            album1.setCount(chapterCount);
        }
        int count = albumDao.selectCount(album);

        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("page",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public String add(Album album) {
        album.setId(UUID.randomUUID().toString());
        album.setCount(0);
        albumDao.insertSelective(album);
        return album.getId();
    }

    @Override
    public String update(Album album, HttpServletRequest request) {
        Album album1 = albumDao.selectByPrimaryKey(album);
        if(!"".equals(album.getCover())){
            File  file = new File(request.getServletContext().getRealPath("/album/img"),album1.getCover());
            file.delete();
            albumDao.updateByPrimaryKeySelective(album);
        }else{
            album.setCover(album1.getCover());
            albumDao.updateByPrimaryKeySelective(album);
        }
        return album.getId();
    }

    @Override
    public void delete(String id, HttpServletRequest request) {
        Album album = albumDao.selectByPrimaryKey(id);
        albumDao.deleteByPrimaryKey(id);
        String cover = album.getCover();
        File file = new File(request.getServletContext().getRealPath("/album/img"),cover);
        file.delete();
    }

    @Override
    public Album selectOne(String id) {
        return albumDao.selectByPrimaryKey(id);
    }
}
