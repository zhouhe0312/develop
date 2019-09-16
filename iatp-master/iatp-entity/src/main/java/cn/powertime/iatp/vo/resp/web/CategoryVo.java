package cn.powertime.iatp.vo.resp.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryVo implements Serializable {

    private Long id;

    private String name;

    private String value;

    private Long count;

}
