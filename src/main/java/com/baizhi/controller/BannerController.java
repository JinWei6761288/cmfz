package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;



    @RequestMapping("findAll")
    @ResponseBody
    public  Map<String,Object> findAll(Integer page, Integer rows) {
        Banner banner = new Banner();
        List<Banner> list = bannerService.findAll(page,rows);
        int totalCounts = bannerService.findCount(banner);
        Map<String,Object> result = new HashMap<>();
        result.put("rows",list);
        result.put("page",page);
        int totalPage = totalCounts%rows==0?totalCounts/rows:totalCounts/rows+1;
        result.put("total",totalPage);
        result.put("records",totalCounts);
        return result;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(Banner banner,String oper,HttpServletRequest request){
        System.out.println("当前接收数据: "+banner);
        System.out.println("当前操作: "+oper);
        Map<String, Object> map = new HashMap<>();
        try {
            if("add".equals(oper)){
                String id = bannerService.save(banner);
                map.put("message",id);
            }
            if("edit".equals(oper)){
                String id = bannerService.update(banner,request);
                map.put("message",id);
            }
            if("del".equals(oper)){
                bannerService.delete(banner.getId(), request);
            }
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile cover, String id, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try {
//            文件上传
            if(!cover.getOriginalFilename().equals("")){
                cover.transferTo(new File(request.getServletContext().getRealPath("/banner/img"),cover.getOriginalFilename()));
//            修改banner对象中cover属性
                Banner banner = new Banner();
                banner.setId(id);
                banner.setCover(cover.getOriginalFilename());
                System.out.println(banner);
                bannerService.update(banner,request);
                map.put("status",true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
}
