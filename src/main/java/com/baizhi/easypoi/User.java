package com.baizhi.easypoi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain =true)
public class User {
    @Excel(name = "编号")
    private String id;
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "生日",format = "yyyy-MM-dd")
    private Date bit;
    @Excel(name = "头像",type = 2)
    private String photo;
}
