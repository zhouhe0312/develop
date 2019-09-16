package cn.powertime.iatp.vo.req.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseCourseSearchVo implements Serializable {

    @ApiModelProperty(value = "课程名称", position = 1)
    private String courseName;

    @ApiModelProperty(value = "课程类型,1 课程，2：实验", position = 2)
    private Long type;
}
