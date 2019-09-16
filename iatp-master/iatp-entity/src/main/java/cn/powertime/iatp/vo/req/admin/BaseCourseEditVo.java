package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BaseCourseEditVo implements Serializable {

    /**
     * 课程（实验）ID
     */
    @NotNull(message = "ID不能为空")
    @ApiModelProperty(value = "课程（实验）ID", position = 1)
    private Long id;

    /**
     * 所属父分类
     */
    @NotNull(message = "所属父分类不能为空")
    @ApiModelProperty(value = "所属父分类", position = 2)
    private Long classifyPid;

    /**
     * 类型
     */
    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型，1 课程，2：实验", position = 3)
    private Integer type;

    /**
     * 所属分类
     */
    @NotNull(message = "所属分类不能为空")
    @ApiModelProperty(value = "所属分类", position = 4)
    private Long classifyId;

    /**
     * 课程（实验）名称
     */
    @NotBlank(message = "课程（实验）名称不能为空")
    @ApiModelProperty(value = "课程（实验）名称", position = 5)
    @Length(min = 1, max = 50, message = "名称不能超过50个字符")
    private String courseName;

    /**
     * 课程（实验）封面
     */
    //@NotNull(message = "课程（实验）封面不能为空")
    @ApiModelProperty(value = "课程（实验）封面", position = 6)
    private Long fileId;

    @ApiModelProperty(value = "简介", position = 7)
    @Length(max = 200, message = "简介不能超过200个字符")
    private String introduce;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
