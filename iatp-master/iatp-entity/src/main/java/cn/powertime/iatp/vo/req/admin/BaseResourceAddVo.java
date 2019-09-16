package cn.powertime.iatp.vo.req.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BaseResourceAddVo implements Serializable {

    /**
     * 类型
     */
    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型，1：课程，2：实验，3：实训", position = 1)
    private Integer type;

    /**
     * 是否默认打开 1：是，2：否
     */
    @NotNull(message = "是否默认打开不能为空")
    @ApiModelProperty(value = "是否默认打开 1：是，2：否", position = 2)
    private Integer defaults;

    /**
     * 课程（实验）ID
     */
    @NotNull(message = "课程（实验）ID不能为空")
    @ApiModelProperty(value = "课程（实验）ID", position = 3)
    private Long courseId;

    /**
     * 章节ID
     */
    @NotNull(message = "章节ID不能为空")
    @ApiModelProperty(value = "章节ID", position = 4)
    private Long chapterPid;

    /**
     * 小节ID
     */
    @NotNull(message = "小节ID不能为空")
    @ApiModelProperty(value = "小节ID", position = 5)
    private Long chapterId;

    /**
     * 资源名称
     */
    @NotNull(message = "文件名称不能为空")
    @ApiModelProperty(value = "文件名称", position = 6)
    @Length(max = 50, message = "文件名称不能超过50个字符")
    private String resourceName;

    /**
     * 资源介绍
     */
    @ApiModelProperty(value = "资源介绍", position = 7)
    @Length(max = 200, message = "资源介绍不能超过200个字符")
    private String introduce;

    /**
     * 资源类型，1：视频，2：ppt，3：pdf，4:实训
     */
    @NotNull(message = "资源类型不能为空")
    @ApiModelProperty(value = "资源类型，1：视频，2：ppt，3：pdf，4:实训", position = 8)
    private Integer resourceType;

    /**
     * 资源文件
     */
    @NotNull(message = "资源文件ID不能为空")
    @ApiModelProperty(value = "资源文件ID", position = 9)
    private Long fileId;

    /**
     * 资源文件扩展名
     */
    @NotBlank(message = "资源文件扩展名不能为空")
    @ApiModelProperty(value = "资源文件扩展名", position = 10)
    private String fileExtensions;

    /**
     * 资源大小 单位：秒（视频），页数（pdf/ppt）
     */
    @NotNull(message = "资源大小不能为空")
    @ApiModelProperty(value = "资源大小 单位：秒（视频），页数（pdf/ppt）", position = 11)
    @Max(value = 9999, message = "数字大小不能超过9999")
    private Long resourceSize;

}
