package com.baizhi.service.impl;

import com.baizhi.dao.StarDao;
import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
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
public class StarServiceImpl implements StarService {
    @Autowired
    private StarDao starDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findAll(Integer page,Integer rows) {
        Star star = new Star();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Star> list = starDao.selectByRowBounds(star, rowBounds);
        int count = starDao.selectCount(star);
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public String add(Star star) {
        star.setId(UUID.randomUUID().toString());
        star.setBir(new Date());
        int i = starDao.insertSelective(star);
        if(i==0){
            throw new RuntimeException("添加失败!");
        }
        return star.getId();
    }

    @Override
    public String update(Star star,HttpServletRequest request) {
        Star star1 = starDao.selectByPrimaryKey(star);
        if(!"".equals(star.getPhoto())){
            File file = new File(request.getServletContext().getRealPath("/star/img"),star1.getPhoto());
            file.delete();
            starDao.updateByPrimaryKeySelective(star);
        }else {
            star.setPhoto(star1.getPhoto());
            starDao.updateByPrimaryKeySelective(star);
        }
        return star.getId();
    }

    @Override
    public void delete(String id, HttpServletRequest request) {
        Star star = starDao.selectByPrimaryKey(id);
        starDao.deleteByPrimaryKey(id);
        String photo = star.getPhoto();
        File file = new File(request.getServletContext().getRealPath("/star/img"),photo);
        file.delete();
    }

    @Override
    public  Map<String,Object> finAllName() {
        List<Star> list = starDao.selectAll();
        Map<String,Object> map = new HashMap<>();
        map.put("rows",list);
        return map;
    }
}
