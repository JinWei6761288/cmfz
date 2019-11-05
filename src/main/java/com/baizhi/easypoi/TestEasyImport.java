package com.baizhi.easypoi;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import java.io.File;
import java.util.List;

public class TestEasyImport {
    public static void main(String[] args) {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<Course> list = ExcelImportUtil.importExcel(new File("E:/IDEA/easypoi/course.xls"),Course.class, params);
        list.forEach(course -> System.out.println(course));
    }
}
