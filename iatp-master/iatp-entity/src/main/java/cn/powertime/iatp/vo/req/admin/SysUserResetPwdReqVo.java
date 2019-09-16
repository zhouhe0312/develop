package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 重置密码的Request Vo
 *
 * @author : zyw
 * Date: 2019-07-03
 * Time: 9:34
 */
@Data
public class SysUserResetPwdReqVo implements Serializable {

    @NotNull(message = "系统用户ID不能为空")
    private String id;

    @NotBlank(message = "新密码不能为空")
    @Length(max = 8, message = "新密码长度不能超过8个字符")
    private String newPassword;

    @NotBlank(message = "重复密码不能为空")
    @Length(max = 8, message = "确认密码长度不能超过8个字符")
    private String repPassword;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
