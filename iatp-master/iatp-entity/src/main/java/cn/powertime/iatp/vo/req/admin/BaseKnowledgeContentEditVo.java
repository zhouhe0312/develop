package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BaseKnowledgeContentEditVo implements Serializable {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空")
    @ApiModelProperty(value = "章节ID", required = true, position = 1)
    private Long id;

    /**
     * 章节
     */
    @NotBlank(message = "章节名称不能为空")
    @ApiModelProperty(value = "章节名称", required = true, position = 2)
    @Length(max = 50, message = "章节名称不能超过50个字符")
    private String name;

    /**
     * 内容
     */
    //@NotBlank(message = "内容不能为空")
    @ApiModelProperty(value = "内容", required = false, position = 3)
    @Length(max = 300, message = "内容不能超过300个字符")
    private String content;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
