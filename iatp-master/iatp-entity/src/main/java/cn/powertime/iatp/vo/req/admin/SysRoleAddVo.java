package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : liqi
 * Date: 2018-11-22
 * Time: 20:25
 */
@Data
public class SysRoleAddVo implements Serializable {

    /**
     * 名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Length(max = 30, message = "角色名称不能超过30个字符")
    private String name;

    /**
     * 编码
     */
    @Length(max = 20, message = "角色编码不能超过20个字符")
    private String code;

    /**
     * 备注
     */
    @Length(max = 50, message = "角色描述不能超过50个字符")
    private String des;

    private Integer status;

    //private Integer del;

    //private String resIds;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
