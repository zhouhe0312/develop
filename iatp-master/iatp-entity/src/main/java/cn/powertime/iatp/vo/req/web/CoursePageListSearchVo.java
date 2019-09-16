package cn.powertime.iatp.vo.req.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "搜索条件")
public class CoursePageListSearchVo implements Serializable {
    @ApiModelProperty(value="查询类型 1：热门 2：最新",name="searcType",example="1")
    private Integer searcType;
    @ApiModelProperty(value="分类父级ID",name="classifyPid")
    private Long classifyPid;
    @ApiModelProperty(value="分类ID",name="classifyId")
    private Long classifyId;
    @ApiModelProperty(value="类型 1：课程 2：实验",name="type",example="1")
    private Integer type;

}
