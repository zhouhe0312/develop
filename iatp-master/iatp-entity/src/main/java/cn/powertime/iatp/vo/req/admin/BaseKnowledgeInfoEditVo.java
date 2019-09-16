package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class BaseKnowledgeInfoEditVo implements Serializable {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value = "知识库名称", position = 1)
    @Length(max = 50, message = "名称不能超过50个字符")
    private String name;

    /**
     * 关键词
     */
    @NotBlank(message = "关键词不能为空")
    @ApiModelProperty(value = "关键词", position = 2)
    @Length(max = 20, message = "关键词不能超过20个字符")
    private String keyword;

    /**
     * 摘要
     */
    @NotBlank(message = "摘要不能为空")
    @ApiModelProperty(value = "摘要", position = 3)
    @Length(max = 300, message = "摘要不能超过300个字符")
    private String summary;

    /**
     * 文号
     */
    @NotBlank(message = "文号不能为空")
    @ApiModelProperty(value = "文号", position = 4)
    @Length(max = 50, message = "文号不能超过50个字符")
    private String version;

    /**
     * 状态，1 现行，2 废止，3 试行，0 禁用，-1 删除
     */
    @NotNull(message = "状态不能为空")
    @Min(value = 1, message = "最小是1")
    @Max(value = 3, message = "最大是3")
    @ApiModelProperty(value = "状态，1 现行，2 废止，3 试行，0 禁用，-1 删除", position = 5)
    private Integer status;

    /**
     * 类别
     */
    @NotNull(message = "类别")
    @ApiModelProperty(value = "类别", position = 6)
    private Integer category;

    /**
     * 小类
     */
    @NotNull(message = "小类")
    @TableField(value = "class")
    @ApiModelProperty(value = "小类", position = 7)
    private Integer classes;

    /**
     * 发布单位
     */
    @NotBlank(message = "发布单位不能为空")
    @ApiModelProperty(value = "发布单位", position = 8)
    @Length(max = 50, message = "发布单位不能超过50个字符")
    private String issued;

    /**
     * 发布日期
     */
    @NotNull(message = "发布日期不能为空")
    @ApiModelProperty(value = "发布日期", position = 9)
    private LocalDate releaseDate;

    /**
     * 废止日期
     */
    //@NotNull(message = "废止日期不能为空")
    @ApiModelProperty(value = "废止日期", position = 10)
    private LocalDate discardDate;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
