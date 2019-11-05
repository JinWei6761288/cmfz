package com.baizhi.easypoi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@ExcelTarget(value = "Teacher")

public class Teacher {
    @ExcelIgnore
    private String id;
    @Excel(name = "任课老师",needMerge = true)//needMerge合并
    private String name;

}
