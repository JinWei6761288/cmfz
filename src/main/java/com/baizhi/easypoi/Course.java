package com.baizhi.easypoi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Course {
    @Excel(name = "课程编号",needMerge = true)//needMerge合并
    private String id;
    @Excel(name = "课程名称",needMerge = true)
    private String name;
    @ExcelEntity
    private Teacher teacher;
    @ExcelCollection(name = "选课学生")
    private List<Student> list;
}
