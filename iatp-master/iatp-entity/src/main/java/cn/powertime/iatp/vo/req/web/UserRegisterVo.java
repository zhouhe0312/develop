package cn.powertime.iatp.vo.req.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author : yang xin
 * Date: 2018-06-17
 */
@Data
public class UserRegisterVo implements Serializable {

    @ApiModelProperty(value = "用户名", position = 1)
    @Pattern(regexp = "^[a-zA-Z][0-9a-zA-Z]{3,11}$", message = "用户名不能为空，必须由4-12个字母和数字组成。")
    private String acount;

    @NotBlank(message = "登录密码不能为空")
    @ApiModelProperty(value = "登录密码", position = 2)
    private String password;

    @NotBlank(message = "确认密码不能为空")
    @ApiModelProperty(value = "确认密码", position = 3)
    private String confirmPassword;

}
