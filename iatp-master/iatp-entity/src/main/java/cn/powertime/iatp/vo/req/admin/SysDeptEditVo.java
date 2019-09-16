package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SysDeptEditVo implements Serializable {

    @NotNull(message = "ID不能为空")
    private Long id;

    @NotNull(message = "父级ID不能为空")
    private Long pid;

    @NotBlank(message = "部门名称不能为空")
    @Length(max = 20, message = "部门名称不能超过20个字符")
    private String name;

    @NotBlank(message = "部门编码不能为空")
    @Length(max = 10, message = "部门编码不能超过20个字符")
    private String code;

    @NotNull(message = "状态不能为空")
    @Min(value = 0, message = "最小为0")
    @Max(value = 1, message = "最大为1")
    private Integer status;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
