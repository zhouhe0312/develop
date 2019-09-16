package cn.powertime.iatp.vo.req.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Data
@ApiModel(description="课程资源查询")
public class ResourceSearchVo implements Serializable {

    @ApiModelProperty(value="课程ID",name="courseId")
    @NotNull(message = "课程ID不能为空")
    private Long courseId;

    @ApiModelProperty(value="章节ID",name="chapterPid")
    @NotNull(message = "章节ID不能为空")
    private Long chapterPid;

//    @ApiModelProperty(value="小节ID",name="chapterId")
//    @NotNull(message = "小节ID不能为空")
//    private Long chapterId;


}
