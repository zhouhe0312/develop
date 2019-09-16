package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BaseClassifyAddVo implements Serializable {


    /**
     * 所属分类
     */
    @NotNull(message = "所属分类不能为空")
    private Long pid;

    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空")
    @Length(max = 50, message = "分类名称不能超过50个字符")
    private String name;

    /**
     * 分类类型，1：课程，2：实验
     */
    @NotNull(message = "类型不能为空")
    private Integer type;

    /**
     * 描述
     */
    @Length(max = 200, message = "描述不能超过200个字符")
    private String des;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
