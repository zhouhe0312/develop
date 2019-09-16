package cn.powertime.iatp.vo.req.admin;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BuildPaperVo implements Serializable {

    @Valid
    @NotNull(message = "试题ID不能为空")
    private Long topicId;
    @Valid
    @NotNull(message = "分值不能为空")
    private Integer scoreValue;

}
