package com.baizhi.service.impl;

import com.baizhi.annotation.ClearRedisCache;
import com.baizhi.annotation.RedisCache;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @RedisCache
    public List<Banner> findAll(Integer page, Integer rows) {
        //count = bannerDao.selectCount(banner);
        Banner banner = new Banner();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        return bannerDao.selectByRowBounds(banner,rowBounds);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public int findCount(Banner banner) {
        return bannerDao.selectCount(banner);
    }

    @Override
    @ClearRedisCache
    public String save(Banner banner) {
        banner.setId(UUID.randomUUID().toString());
        banner.setCreateTime(new Date());
        int i = bannerDao.insertSelective(banner);
        if(i==0){
            throw new RuntimeException("添加失败!");
        }
        return banner.getId();
    }

    @Override
    @ClearRedisCache
    public String update(Banner banner,HttpServletRequest request) {
        Banner banner1 = bannerDao.selectByPrimaryKey(banner);
        if(!"".equals(banner.getCover())){
            File file = new File(request.getServletContext().getRealPath("/banner/img"),banner1.getCover());
            file.delete();
            bannerDao.updateByPrimaryKeySelective(banner);
        }else {
            try {
                banner.setCover(banner1.getCover());
                bannerDao.updateByPrimaryKeySelective(banner);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("修改失败");
            }
        }

        return banner.getId();

    }

    @Override
    @ClearRedisCache
    public void delete(String id ,HttpServletRequest request) {
        Banner banner = bannerDao.selectByPrimaryKey(id);
        int i = bannerDao.deleteByPrimaryKey(id);
        if(i==0){
            throw new RuntimeException("删除失败");
        }else{
            String cover = banner.getCover();
            File file = new File(request.getServletContext().getRealPath("/banner/img"),cover);
            file.delete();
        }
    }


}
