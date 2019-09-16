package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BaseTopicEditVo implements Serializable {

    /**
     * 试题ID
     */
    @NotNull(message = "ID不能为空")
    private Long id;
    /**
     * 题库类型ID
     */
    @NotNull(message = "题库ID不能为空")
    private Long typeId;

    /**
     * 试题题干
     */
    @NotBlank(message = "试题题干不能为空")
    private String topicStem;

    /**
     * 试题答案
     */
    @NotBlank(message = "试题答案不能为空")
    private String topicAnswer;

    @Min(value = 1,message = "分值不能小于1分")
    @Max(value = 100,message = "分值不能大于100分")
    private Integer scoreValue;

    /**
     * A选项
     */
    @Length(max = 500,message = "A选项长度不能超过500")
    private String optionA;

    /**
     * B选项
     */
    @Length(max = 500,message = "B选项长度不能超过500")
    private String optionB;

    /**
     * C选项
     */
    @Length(max = 500,message = "C选项长度不能超过500")
    private String optionC;

    /**
     * D选项
     */
    @Length(max = 500,message = "D选项长度不能超过500")
    private String optionD;

    /**
     * 试题解析
     */
    @NotBlank(message = "试题解析不能为空")
    private String topicAnalysis;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
