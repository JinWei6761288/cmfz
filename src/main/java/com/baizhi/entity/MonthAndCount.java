package com.baizhi.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class MonthAndCount implements Serializable {
    private Integer month;
    private Integer count;
}
