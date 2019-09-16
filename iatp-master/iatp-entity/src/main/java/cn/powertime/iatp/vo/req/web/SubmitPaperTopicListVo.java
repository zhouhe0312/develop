package cn.powertime.iatp.vo.req.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "试题选项")
public class SubmitPaperTopicListVo implements Serializable {

    @NotNull(message = "试卷试题ID不能为空")
    @ApiModelProperty(value="试卷试题ID",name="id")
    private Long id;
    @NotNull(message = "题库试题ID不能为空")
    @ApiModelProperty(value="题库试题ID",name="topicId")
    private Long topicId;
    //@NotBlank(message = "用户选项不能为空")
    @ApiModelProperty(value="用户选项",name="answer")
    private String answer;
    @NotNull(message = "试题分值不能为空")
    @ApiModelProperty(value="试题分值",name="score")
    private Integer score;

}
