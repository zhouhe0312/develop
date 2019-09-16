package cn.powertime.iatp.vo.req.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description="错题搜索对象")
public class WrongQuestionsSeachVo implements Serializable {
    @ApiModelProperty(value="题干",name="title")
    private String title;

    /**
     * 1:课程随堂，2：课程单元，3：课程，4：实验随堂，5：实验单元，6：实验，7：综合
     */
    @ApiModelProperty(value="试题类型 1:课程随堂，2：课程单元，3：课程，4：实验随堂，5：实验单元，6：实验，7：综合",name="type")
    private Integer type;

}
