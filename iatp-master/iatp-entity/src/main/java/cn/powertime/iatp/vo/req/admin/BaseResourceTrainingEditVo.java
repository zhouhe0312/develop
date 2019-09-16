package cn.powertime.iatp.vo.req.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class BaseResourceTrainingEditVo implements Serializable {

    /**
     * 课程（实验）资源ID
     */
    @NotNull(message = "课程（实验）资源ID不能为空")
    @ApiModelProperty(value="课程（实验）资源ID", position = 1)
    private Long id;

    /**
     * 类型
     */
    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型，1：课程，2：实验，3：实训", position = 2)
    private Integer type;

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
    @NotNull(message = "实训名称不能为空")
    @ApiModelProperty(value = "实训名称", position = 6)
    @Length(max = 50, message = "实训名称不能超过50个字符")
    private String resourceName;

    /**
     * 资源类型，1：视频，2：ppt，3：pdf，4:实训
     */
    @NotNull(message = "资源类型不能为空")
    @ApiModelProperty(value = "资源类型，1：视频，2：ppt，3：pdf，4:实训", position = 7)
    private Integer resourceType;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", position = 8)
    @Length(max = 50, message = "用户名不能超过50个字符")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", position = 9)
    @Length(max = 20, message = "密码不能超过20个字符")
    private String password;

    /**
     * IP
     */
    @NotBlank(message = "IP不能为空")
    @ApiModelProperty(value = "IP", position = 10)
    @Pattern(regexp = "((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}", message = "IP地址不合法")
    private String ip;

    /**
     * 端口
     */
    @NotBlank(message = "服务器端口不能为空")
    @ApiModelProperty(value = "端口", position = 11)
    @Pattern(regexp = "^([1-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$", message = "端口不合法，取值范围1-65535")
    private String port;

    /**
     * 服务端口
     */
    @NotBlank(message = "webConsole服务端口不能为空")
    @ApiModelProperty(value = "webConsole服务端口", position = 12)
    @Pattern(regexp = "^([1-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$", message = "服务端口不合法，取值范围1-65535")
    private String servicePort;

}
