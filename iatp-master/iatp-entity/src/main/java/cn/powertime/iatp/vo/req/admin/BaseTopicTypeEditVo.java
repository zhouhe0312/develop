package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BaseTopicTypeEditVo implements Serializable {

    @NotNull(message = "ID不能为空")
    private Long id;
    @NotBlank(message = "名称不能为空")
    @Length(max = 200,message = "名称长度不能超过200")
    private String title;

    private String des;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
