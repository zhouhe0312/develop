package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BaseChapelTestAddVo implements Serializable {

    /**
     * 试卷名称
     */
    @NotBlank(message = "试卷名称不能为空")
    private String title;

    /**
     * 归属课程/实验ID
     */
    private Long courseId;

    /**
     * 归属章节ID
     */
    private Long chapterId;

    /**
     * 小节ID
     */
    private Long subsection;

    /**
     * 考试时长
     */
    @NotNull(message = "考试时长不能为空")
    @Max(value = 120,message = "考试时长不能大于120分钟")
    @Min(value = 1,message = "考试时长不能小于1分钟")
    private Long paperDuration;

    /**
     * 总分数
     */
    @NotNull(message = "总分数不能为空")
    @Max(value = 100,message = "分数不能大于100分")
    @Min(value = 1,message = "分数不能小于1分")
    private Integer score;

    /**
     * 归属课程ID
     */
    private Long courseOneId;

    /**
     * 归属实验ID
     */
    private Long courseTwoId;

    /**
     * 类型，1:课程随堂，2：课程单元，3：课程，4：实验随堂，5：实验单元，6：实验，7：综合
     */
    @NotNull(message = "考试类型不能为空")
    @Min(value = 1 , message = "最小是1")
    @Max(value = 7 , message = "最大是7")
    private Integer testType;

//    /**
//     * 类型，1 课程，2：实验
//     */
//    @NotNull(message = "考试归属类型不能为空")
//    @Min(value = 1 , message = "最小是1")
//    @Max(value = 2 , message = "最大是2")
//    private Integer type;


    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
