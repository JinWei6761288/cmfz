package com.baizhi.service.impl;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.MonthAndCount;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectUsersByStarId(Integer page, Integer rows, String starId) {
        User user = new User();
        user.setStarId(starId);
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<User> list = userDao.selectByRowBounds(user, rowBounds);
        int count = userDao.selectCount(user);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findAll(Integer page, Integer rows) {
        User user = new User();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<User> list = userDao.selectByRowBounds(user, rowBounds);
        int count = userDao.selectCount(user);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public void update(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<User> selectAll() {
        return userDao.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Integer> selectSexAadMonth(String sex) {
        List<Integer> list = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
        List<MonthAndCount> sexList = userDao.selectSexAadMonth(sex);
        for (int i = 0; i < sexList.size(); i++) {
            Integer month = sexList.get(i).getMonth();
            for (int j = 1; j < 13; j++) {
                if(month==j){
                    list.set(j-1,sexList.get(i).getCount());
                }
            }
        }
        return list;
    }

}
