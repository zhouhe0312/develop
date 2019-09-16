package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BaseCourseAddVo implements Serializable {

    /**
     * 所属父分类
     */
    @NotNull(message = "所属父分类不能为空")
    @ApiModelProperty(value = "所属父分类", position = 1)
    private Long classifyPid;

    /**
     * 类型
     */
    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型，1 课程，2：实验", position = 2)
    private Integer type;

    /**
     * 所属分类
     */
    @NotNull(message = "所属分类不能为空")
    @ApiModelProperty(value = "所属分类", position = 3)
    private Long classifyId;

    /**
     * 课程（实验）名称
     */
    @NotBlank(message = "课程（实验）名称不能为空")
    @ApiModelProperty(value = "课程（实验）名称", position = 4)
    @Length(max = 50, message = "名称不能超过50个字符")
    private String courseName;

    //@NotNull(message = "课程（实验）封面不能为空")
    @ApiModelProperty(value = "课程（实验）封面", position = 5)
    private Long fileId;

    @ApiModelProperty(value = "简介", position = 6)
    @Length(max = 200, message = "简介不能超过200个字符")
    private String introduce;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
