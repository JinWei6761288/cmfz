package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Star implements Serializable {
    @Id
    private String id;
    private String name;
    private String photo;
    private String nickname;
    private String sex;
    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
    private Date bir;
}
