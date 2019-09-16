package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author liqi
 * @since 2019-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    //@NotBlank(message = "姓名不能为空")
    @Pattern(regexp = "[\\u4e00-\\u9fa5]{2,6}", message = "姓名不能为空，必须由2-6个汉字组成")
    private String username;

    /**
     * 帐号
     */
    //@NotBlank(message = "帐号不能为空")
    //@Pattern(regexp = "([\\u4e00-\\u9fa5]{2,6})|([A-Za-z]{4,12})", message = "帐号不能为空，必须由4-12个字母或2-6个汉字组成。")
    @Pattern(regexp = "^[a-zA-Z][0-9a-zA-Z]{3,11}$", message = "帐号不能为空，必须由4-12个字母和数字组成。")
    private String acount;

    /**
     * 昵称
     */
    //@Pattern(regexp = "([\\u4e00-\\u9fa5]{2,10})|([A-Za-z0-9]{4,12})", message = "昵称必须由4-12个字母和数字或2-10个汉字组成")
    @Length(max = 12, message = "昵称不能超过12个字符")
    private String nickname;

    /**
     * 邮箱
     */
    //@Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 手机
     */
    //@Pattern(regexp = "^[1][3,4,5,6,7,8][0-9]{9}$", message = "手机号不合法")
    private String phone;

    /**
     * 1:男，0:女
     */
    private Integer sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String password;

    @NotNull(message = "重复密码不能为空")
    private String repeatPassword;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 所属单位ID
     */
    private Long unitId;

    @NotNull(message = "账号状态不能为空")
    @Min(value = 0, message = "最小值为0")
    @Max(value = 1, message = "最大值为1")
    private Integer status;

    @NotNull(message = "用户类型不能为空")
    @Min(value = 1, message = "最小值为1")
    @Max(value = 2, message = "最大值为2")
    private Integer type;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
