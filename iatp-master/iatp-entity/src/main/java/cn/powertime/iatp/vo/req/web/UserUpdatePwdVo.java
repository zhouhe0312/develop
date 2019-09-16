package cn.powertime.iatp.vo.req.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : liqi
 * Date: 2018-06-03
 * Time: 11:34
 */
@Data
public class UserUpdatePwdVo implements Serializable {


    @NotNull(message = "系统用户ID不能为空")
    @ApiModelProperty(value = "ID", position = 1)
    private String id;

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(value = "账号", position = 2)
    private String acount;

    @NotBlank(message = "旧密码不能为空")
    @ApiModelProperty(value = "旧密码", position = 3)
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码", position = 4)
    private String newPassword;

    @NotBlank(message = "重复密码不能为空")
    @ApiModelProperty(value = "重复密码", position = 5)
    private String repPassword;

}
