package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BaseChapterAddVo implements Serializable {

    /**
     * 所属章节
     */
    @NotNull(message = "所属章节不能为空")
    @ApiModelProperty(value = "所属章节id", required = true, position = 1)
    private Long pid;

    /**
     * 类型，1 课程，2：实验
     */
    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型，1 课程，2：实验", required = true, position = 2)
    private Integer type;

    /**
     * 章节名称
     */
    @NotBlank(message = "章节名称不能为空")
    @ApiModelProperty(value = "章节名称", required = true, position = 3)
    @Length(max = 50, message = "名称不能超过50个字符")
    private String chapterName;

    /**
     * 课程（实验）ID
     */
    @NotNull(message = "课程（实验）ID不能为空")
    @ApiModelProperty(value = "课程（实验）ID", required = true, position = 4)
    private Long courseId;

    /**
     * 内容
     */
    //@NotBlank(message = "内容不能为空")
    @ApiModelProperty(value = "内容简介", required = false, position = 5)
    @Length(max = 200, message = "简介不能超过200个字符")
    private String des;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", position = 7)
    private Integer sortNum;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
