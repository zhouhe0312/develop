package cn.powertime.iatp.vo.req.admin;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class BuildPaperList implements Serializable {

    @NotNull(message = "试卷ID不能为空")
    private Long testPaperId;

    @Valid
    @NotEmpty(message = "组卷信息不能为空")
    private List<BuildPaperVo> list;

}
