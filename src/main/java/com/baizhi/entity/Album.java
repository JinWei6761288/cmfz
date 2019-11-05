package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Album {

    @Id
    private String id;
    private String title;
    private String cover;
    private Integer count;
    private Double score;
    private String author;
    private String broadcast;
    private String brief;
    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
}
