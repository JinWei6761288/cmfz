package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.AliyunMessageUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;


    //查询明星下所有用户
    @RequestMapping("findAll")
    @ResponseBody
    public Map<String, Object> findAllByStarId(Integer page, Integer rows, String starId) {
        Map<String, Object> map = userService.selectUsersByStarId(page, rows, starId);
        return map;
    }

    //用户模块查询所有用户
    @RequestMapping("findAllUser")
    @ResponseBody
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Map<String, Object> map = userService.findAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public void update(User user, String oper) {
        userService.update(user);
    }


    //easypoi导出
    @RequestMapping("export")
    public void export(HttpServletRequest request,HttpServletResponse response) throws Exception {
        //准备数据
        List<User> users = userService.selectAll();
        String realPath = request.getServletContext().getRealPath("/user/img");
        for (User user : users) {
            user.setPhoto(realPath+"/"+user.getPhoto());
            if("y".equals(user.getStatus())){
                user.setStatus("正常");
            }else{
                user.setStatus("冻结");
            }
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("所有用户","用户"), User.class, users);

        String fileName = "用户报表("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+").xls";
        //处理中文下载名乱码
        try {
            fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
            //设置 response
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-disposition","attachment;filename="+fileName);
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //注册趋势(Echarts)
    @RequestMapping("UserEcharts")
    @ResponseBody
    public Map<String, Object> selectSexAadMonth(){
        Map<String,Object> map = new HashMap<>();
        List<Integer> man = userService.selectSexAadMonth("男");
        List<Integer> nv = userService.selectSexAadMonth("女");
        map.put("man",man);
        map.put("nv",nv);
        return map;
    }

    //用户注册
    @RequestMapping("regist")
    public String sendMeg(String phone,HttpServletRequest request) throws ClientException {
        String phoneNumber = phone;
        String random = createRandomNum(6);
        System.out.println(random);
        String jsonContent = "{\"code\":\"" + random + "\"}";
        Map<String, String> map = new HashMap<>();
        map.put("phoneNumber",phoneNumber);
        map.put("msgSign","小当当");
        map.put("templateCode",random);
        map.put("jsonContent",jsonContent);
        SendSmsResponse sendSmsResponse = AliyunMessageUtil.sendSms(map);
        if(!(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK"))) {
            if(sendSmsResponse.getCode() == null) {
               // throw new RuntimeException("验证码不能为空");
                //这里可以抛出自定义异常
            }
            if(!sendSmsResponse.getCode().equals("OK")) {
                //这里可以抛出自定义异常
               // throw new RuntimeException("验证码错误");
            }
        }
        return "成功";
    }






    /**
     * 生成随机数
     * @param num 位数
     * @return
     */
    public static String createRandomNum(int num){
        String randomNumStr = "";
        for(int i = 0; i < num;i ++){
            int randomNum = (int)(Math.random() * 10);
            randomNumStr += randomNum;
        }
        return randomNumStr;
    }
}
