package cn.powertime.iatp.vo.req.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Data
@ApiModel(description="学习进度记录")
public class RecordVo implements Serializable {

    /**
     * 课程（实验）ID
     */
    @ApiModelProperty(value="课程（实验）ID",name="courseId")
    @NotNull(message = "课程（实验）ID不能为空")
    private Long courseId;

    /**
     * 章节ID
     */
    @ApiModelProperty(value="章节ID",name="chapterId")
    @NotNull(message = "章节ID不能为空")
    private Long chapterId;

    /**
     * 节ID
     */
    @ApiModelProperty(value="节ID",name="sectionId")
    @NotNull(message = "节ID不能为空")
    private Long sectionId;

    /**
     * 资源ID
     */
    @ApiModelProperty(value="资源ID",name="resourceId")
    @NotNull(message = "资源I不能为空")
    private Long resourceId;

    /**
     * 学习进度 单位：秒（视频），页数（pdf/ppt）
     */
    @ApiModelProperty(value="学习进度 单位：秒（视频），页数（pdf/ppt）",name="schedule")
    @NotNull(message = "学习进度不能为空")
    private Long schedule;

    /**
     * 学习状态，1 正在学习，2：学习完成
     */
    @ApiModelProperty(value="学习状态，1 正在学习，2：学习完成",name="type")
    @NotNull(message = "学习状态不能为空")
    private Integer type;

}
