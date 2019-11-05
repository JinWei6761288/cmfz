package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @RequestMapping("login")
    public Map<String,Object> login(HttpServletRequest request, Admin admin, String inputCode){
        Map<String,Object> map = new HashMap<>();
        try {
            adminService.login(request,admin,inputCode);
            map.put("status",true);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }

}
