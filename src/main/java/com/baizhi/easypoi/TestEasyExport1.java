package com.baizhi.easypoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestEasyExport1 {
    public static void main(String[] args) throws Exception {
      List<User> list = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            User user = new User();
            user.setId(i+"");
            user.setName("张三"+i);
            user.setBit(new Date());
            user.setPhoto("C:/Users/Administrator/Desktop/明星项目图片/专辑封面/薛之谦.jpg");
            list.add(user);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),User.class, list);
        workbook.write(new FileOutputStream(new File("E:/IDEA/easypoi/user.xls")));
    }
}
