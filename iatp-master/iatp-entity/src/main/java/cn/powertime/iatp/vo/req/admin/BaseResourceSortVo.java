package cn.powertime.iatp.vo.req.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BaseResourceSortVo implements Serializable {

    /**
     * 上一个id
     */
    @NotNull(message = "prevId不能为空")
    @ApiModelProperty(value = "上一个id")
    private Long prevId;

    /**
     * 下一个id
     */
    @NotNull(message = "nextId不能为空")
    @ApiModelProperty(value = "下一个id")
    private Long nextId;

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id")
    private Long id;

}
