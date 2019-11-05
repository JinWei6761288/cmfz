package com.baizhi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("exit")
public class ExitController {

    //安全退出
    @RequestMapping("exit")
    public String exit(HttpSession session){
        session.invalidate();
        return "redirect:/login/login.jsp";
    }
}
