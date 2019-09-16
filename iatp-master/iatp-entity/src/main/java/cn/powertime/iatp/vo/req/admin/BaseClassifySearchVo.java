package cn.powertime.iatp.vo.req.admin;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseClassifySearchVo implements Serializable {

    private Long pid;

    private String name;
    
}
