package cn.powertime.iatp.vo.resp.admin;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ZYW
 */
@Data
public class WrongQuestionsRespListVo implements Serializable {
    /**
     * 错题表ID
     */
    public Long id;

    /**
     * 错题用户名称
     */
    private String userName;
    /**
     * 错题用户姓名
     */
    private String name;

    /**
     * 题干
     */
    private String topicStem;

    /**
     * 所属课程/实验
     */
    private String courseName;

    /**
     * 试卷名称
     */
    private String title;

    /**
     * 用户选择选项
     */
    private String topicAnswer;

    /**
     * 正确选项
     */
    private String correctOption;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
