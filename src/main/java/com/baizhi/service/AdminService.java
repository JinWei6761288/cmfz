package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {
    void login(HttpServletRequest request,Admin admin,String inputCode);
}
