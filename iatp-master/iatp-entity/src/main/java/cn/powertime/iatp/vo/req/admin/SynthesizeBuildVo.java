package cn.powertime.iatp.vo.req.admin;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SynthesizeBuildVo implements Serializable {

    @NotNull(message = "试卷ID不能为空")
    private Long chapelTestId;

    private Long courseId;

}
