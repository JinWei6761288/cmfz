package com.baizhi.service.impl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    public void login(HttpServletRequest request, Admin admin, String inputCode) {
        String code = (String) request.getSession().getAttribute("securityCode");
        if(inputCode.equalsIgnoreCase(code)){
            Admin admin1 = adminDao.selectOne(admin);
            if(admin1==null){
                throw new RuntimeException("用户名不存在!");
            }else if(!admin.getPassword().equals(admin1.getPassword())){
                throw new RuntimeException("密码错误!");
            }else{
                request.getSession().setAttribute("admin",admin1);
            }
        }else{
            throw new RuntimeException("验证码错误!");
        }
    }
}
