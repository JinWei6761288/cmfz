package com.baizhi.easypoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestEasyExport2 {
    public static void main(String[] args) throws Exception {

        //模拟数据
        List<Course> list = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            Course course = new Course();
            course.setId("c"+i);
            course.setName("海贼王必修课"+i);
            course.setTeacher(new Teacher().setId("t"+i).setName("老张"+i));
            List<Student> students = new ArrayList<>();
            for (int i1 = 1; i1 < 4; i1++) {
                Student student = new Student();
                student.setId("s"+i);
                student.setName("小坤"+i);
                student.setSex("男");
                student.setBir(new Date());
                students.add(student);
            }
            course.setList(students);
            list.add(course);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("测试1","测试2","测试"),Course.class, list);
        workbook.write(new FileOutputStream(new File("E:/IDEA/easypoi/course.xls")));
    }
}
