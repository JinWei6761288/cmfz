package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class User implements Serializable {
    @Id
    @Excel(name = "编号")
    private String id;
    @Excel(name = "头像",type = 2)
    private String photo;
    @Excel(name = "昵称")
    private String dharmaname;
    @Excel(name = "真实姓名")
    private String realname;
    @Excel(name = "性别")
    private String sex;
    @Excel(name = "省")
    private String privoince;
    @Excel(name = "市")
    private String city;
    @Excel(name = "签名")
    private String sign;
    @Excel(name = "手机号")
    private String phonenum;
    private String password;
    private String salt;
    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
    @Excel(name = "生日",format = "yyyy-MM-dd")
    private Date creatTime;
    @Excel(name = "状态")
    private String status;
    private String starId;
}
