package cn.powertime.iatp.vo.req.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseKnowledgeInfoSearchVo implements Serializable {

    @ApiModelProperty(value="知识库名称")
    private String name;

    @ApiModelProperty(value="知识库类别")
    private Integer category;
    
}
