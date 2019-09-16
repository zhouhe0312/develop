package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : liqi
 * Date: 2018-05-09
 * Time: 9:34
 */
@Data
public class SysUserUpdatePwdVo implements Serializable {


    @NotBlank(message = "系统用户ID不能为空")
    private String id;
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
    @NotBlank(message = "重复密码不能为空")
    private String repPassword;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
