package cn.powertime.iatp.vo.req.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BaseCourseUploadVo implements Serializable {

    /**
     * 所属父分类
     */
    @NotNull(message = "所属父分类不能为空")
    @ApiModelProperty(value = "所属父分类", position = 1)
    private Long classifyPid;

    /**
     * 所属分类
     */
    @NotNull(message = "所属分类不能为空")
    @ApiModelProperty(value = "所属分类", position = 2)
    private Long classifyId;

}
