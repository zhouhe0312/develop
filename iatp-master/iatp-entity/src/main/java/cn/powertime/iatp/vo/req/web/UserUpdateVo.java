package cn.powertime.iatp.vo.req.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : yang xin
 * Date: 2018-06-04
 * Time: 13:43
 */
@Data
public class UserUpdateVo implements Serializable {


    @NotNull(message = "系统用户ID不能为空")
    @ApiModelProperty(value = "ID", position = 1)
    private Long id;

    //@NotBlank(message = "昵称不能为空")
    @ApiModelProperty(value = "昵称", position = 2)
    //@Pattern(regexp = "([\\u4e00-\\u9fa5]{2,10})|([A-Za-z0-9]{4,12})", message = "昵称必须由4-12个字母和数字或2-10个汉字组成")
    private String nickname;

    //@NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名", position = 3)
    @Pattern(regexp = "[\\u4e00-\\u9fa5]{2,6}", message = "姓名不能为空，必须由2-6个汉字组成")
    private String username;

    @NotNull(message = "性别不能为空")
    @ApiModelProperty(value = "性别，1:男，0:女", position = 4)
    private Integer sex;

    //@NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号", position = 5)
    //@Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号不合法")
    private String phone;

    //@NotBlank(message = "邮箱不能为空")
    @ApiModelProperty(value = "邮箱", position = 6)
    //@Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty(value = "头像", position = 7)
    private String avatar;

}
