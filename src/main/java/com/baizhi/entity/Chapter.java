package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Chapter implements Serializable {
    @Id
    private String id;
    private String title;
    private String size;
    private String duration;
    private String name;
    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
    private Date createDate;
    private String albumId;

}
