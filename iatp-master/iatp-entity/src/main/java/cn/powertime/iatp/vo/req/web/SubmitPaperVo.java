package cn.powertime.iatp.vo.req.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description="提交试卷对象")
public class SubmitPaperVo implements Serializable {

    @NotNull(message = "试卷ID不能为空")
    @ApiModelProperty(value="试卷ID",name="id")
    private Long id;
    @NotNull(message = "课程ID不能为空")
    @ApiModelProperty(value="课程ID",name="courseId")
    private Long courseId;
    @NotNull(message = "试卷类型不能为空")
    @ApiModelProperty(value="试卷类型",name="type")
    private Integer type;
    @NotNull(message = "开始考试时间不能为空")
    @ApiModelProperty(value="开始考试时间",name="startTime")
    private Long startTime;
    @NotNull(message = "结束考试时间不能为空")
    @ApiModelProperty(value="结束考试时间",name="endtime")
    private Long endtime;
    @Valid
    @NotEmpty(message = "用户选项不能为空")
    private List<SubmitPaperTopicListVo> list;



}
