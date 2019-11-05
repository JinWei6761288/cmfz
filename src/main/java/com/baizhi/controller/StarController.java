package com.baizhi.controller;

import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("star")
public class StarController {

    @Autowired
    private StarService starService;

    @RequestMapping("findAll")
    @ResponseBody
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Map<String, Object> map = starService.findAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map<String, Object> edit(Star star, String oper, HttpServletRequest request) {
        System.out.println("当前接收数据: " + star);
        System.out.println("当前操作: " + oper);
        Map<String, Object> map = new HashMap<>();
        try {
            if ("add".equals(oper)) {
                String id = starService.add(star);
                map.put("message", id);
            }
            if ("edit".equals(oper)) {
                String id = starService.update(star, request);
                map.put("message",id);
            }
            if ("del".equals(oper)) {
                starService.delete(star.getId(), request);
            }
            map.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @RequestMapping("upload")
    public Map<String, Object> upload(MultipartFile photo, String id, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
//            文件上传
            if (!photo.getOriginalFilename().equals("")) {
                photo.transferTo(new File(request.getServletContext().getRealPath("/star/img"), photo.getOriginalFilename()));
//            修改banner对象中cover属性
                Star star = new Star();
                star.setId(id);
                star.setPhoto(photo.getOriginalFilename());
                starService.update(star, request);
                map.put("status", true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status", false);
        }
        return map;
    }

    @RequestMapping("findAllStarName")
    @ResponseBody
    public Map<String,Object> findAllStarName(){
        return starService.finAllName();
    }

}
