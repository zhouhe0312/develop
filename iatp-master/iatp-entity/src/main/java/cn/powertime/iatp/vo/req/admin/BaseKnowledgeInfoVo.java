package cn.powertime.iatp.vo.req.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class BaseKnowledgeInfoVo implements Serializable {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "关键词")
    private String keyword;

    @ApiModelProperty(value = "摘要")
    private String summary;

    @ApiModelProperty(value = "文号")
    private String version;

    @ApiModelProperty(value = "类别")
    private String category;

    @ApiModelProperty(value = "小类")
    private String classes;

    @ApiModelProperty(value = "发布单位")
    private String issued;

    @ApiModelProperty(value = "发布日期")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate releaseDate;

    @ApiModelProperty(value = "废止日期")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate discardDate;

    @ApiModelProperty(value = "附件")
    private String filename;

}
