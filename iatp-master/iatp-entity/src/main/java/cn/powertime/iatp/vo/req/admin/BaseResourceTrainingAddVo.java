package cn.powertime.iatp.vo.req.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class BaseResourceTrainingAddVo implements Serializable {

    /**
     * 类型
     */
    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型，1：课程，2：实验，3：实训", position = 1)
    private Integer type;

    /**
     * 课程（实验）ID
     */
    @NotNull(message = "课程（实验）ID不能为空")
    @ApiModelProperty(value = "课程（实验）ID", position = 2)
    private Long courseId;

    /**
     * 章节ID
     */
    @NotNull(message = "章节ID不能为空")
    @ApiModelProperty(value = "章节ID", position = 3)
    private Long chapterPid;

    /**
     * 小节ID
     */
    @NotNull(message = "小节ID不能为空")
    @ApiModelProperty(value = "小节ID", position = 4)
    private Long chapterId;

    /**
     * 资源名称
     */
    @NotNull(message = "实训名称不能为空")
    @ApiModelProperty(value = "实训名称", position = 5)
    @Length(max = 50, message = "实训名称不能超过50个字符")
    private String resourceName;

    /**
     * 资源类型，1：视频，2：ppt，3：pdf，4:实训
     */
    @NotNull(message = "资源类型不能为空")
    @ApiModelProperty(value = "资源类型，1：视频，2：ppt，3：pdf，4:实训", position = 6)
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
    @NotBlank(message = "IP地址不能为空")
    @ApiModelProperty(value = "IP地址", position = 10)
    @Pattern(regexp = "((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}", message = "IP地址不合法")
    private String ip;

    /**
     * 端口
     */
    @NotBlank(message = "服务器端口不能为空")
    @ApiModelProperty(value = "端口", position = 11)
    //@Max(value = 65535, message = "端口的最大值为65535")
    //@Min(value = 1, message = "端口的最小值为1")
    @Pattern(regexp = "^([1-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$", message = "端口不合法，取值范围1-65535")
    private String port;

    /**
     * 服务端口
     */
    @NotBlank(message = "webConsole服务端口不能为空")
    @ApiModelProperty(value = "webConsole服务端口", position = 12)
    //@Max(value = 65535, message = "服务端口的最大为65535")
    //@Min(value = 1, message = "服务端口的最小值为1")
    @Pattern(regexp = "^([1-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$", message = "服务端口不合法，取值范围1-65535")
    private String servicePort;

}
