package cn.powertime.iatp.vo.resp.web;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CourseExamPageListVo implements Serializable {

    private Long id;
    /**
     *试卷名称
     */
    private String testTitle;
    /**
     * 试卷类型 1:课程随堂，2：课程单元，3：课程，4：实验随堂，5：实验单元，6：实验，7：综合
     */
    private Integer testType;
    /**
     * 归属名称
     */
    private String courseName;
    /**
     * 1：已收藏
     * 2：未收藏
     */
    private Integer collected;
    /**
     * 得分
     */
    private BigDecimal score;
    /**
     * 考试结果表ID
     */
    private Long resultId;


}
