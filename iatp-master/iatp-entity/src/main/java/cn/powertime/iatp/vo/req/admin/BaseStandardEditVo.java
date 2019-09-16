package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BaseStandardEditVo implements Serializable {

    /**
     * ID
     */
    //@NotNull(message = "ID不能为空")
    @ApiModelProperty(value = "ID", position = 1)
    private Long id;

    /**
     * 知识库条目ID
     */
    @NotNull(message = "知识库条目ID不能为空")
    @ApiModelProperty(value = "知识库条目ID", position = 2)
    private Long controlId;

    /**
     * 访谈提问
     */
    //@NotBlank(message = "访谈提问不能为空")
    @ApiModelProperty(value = "访谈提问", position = 3)
    private String question;

    /**
     * 依据条款内容
     */
    //@NotBlank(message = "依据条款内容不能为空")
    @ApiModelProperty(value = "依据条款内容", position = 4)
    private String demain;

    /**
     * 收集审计证据说明
     */
    //@NotBlank(message = "收集审计证据说明不能为空")
    @ApiModelProperty(value = "收集审计证据说明", position = 5)
    private String note;

    /**
     * 存在问题
     */
    //@NotBlank(message = "存在问题不能为空")
    @ApiModelProperty(value = "存在问题", position = 6)
    private String problem;

    /**
     * 答题岗位
     */
    //@NotNull(message = "答题岗位不能为空")
    @ApiModelProperty(value = "答题岗位", position = 7)
    private Integer anwserRoler;

    /**
     * 风险级别
     */
    //@NotNull(message = "风险级别不能为空")
    @ApiModelProperty(value = "风险级别", position = 8)
    private Integer rishLevel;

    /**
     * 风险描述
     */
    //@NotBlank(message = "风险描述不能为空")
    @ApiModelProperty(value = "风险描述", position = 9)
    private String rish;

    /**
     * 建议
     */
    //@NotBlank(message = "建议不能为空")
    @ApiModelProperty(value = "建议", position = 10)
    private String suggest;

    /**
     * 分类：0:管理  1:技术
     */
    //@NotNull(message = "分类不能为空")
    @ApiModelProperty(value = "分类：0:管理  1:技术", position = 11)
    private Integer type;

    /**
     * 主体单位
     */
    //@NotBlank(message = "主体单位不能为空")
    @ApiModelProperty(value = "主体单位", position = 12)
    private String auditUnit;

    /**
     * 依赖其它题的ID号，逗号分割
     */
    //@NotBlank(message = "依赖其它题的ID号不能为空")
    @ApiModelProperty(value = "依赖其它题的ID号，逗号分割", position = 13)
    private String depend;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
