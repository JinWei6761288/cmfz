package com.baizhi.service.impl;

import com.baizhi.annotation.RedisCache;
import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @RedisCache
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Article article = new Article();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Article> list = articleDao.selectByRowBounds(article, rowBounds);
        int count = articleDao.selectCount(article);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("page",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public void add(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreateDate(new Date());
        articleDao.insertSelective(article);

    }

    @Override
    public void edit(Article article) {
        articleDao.updateByPrimaryKeySelective(article);

    }

    @Override
    public void delete(String id) {
        articleDao.deleteByPrimaryKey(id);
    }
}
