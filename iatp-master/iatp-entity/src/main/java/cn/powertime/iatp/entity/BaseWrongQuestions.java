package cn.powertime.iatp.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 错题表
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
public class BaseWrongQuestions implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 课程/实验ID
     */
    private Long courseId;

    /**
     * 试卷ID
     */
    private Long testPaperId;

    /**
     * 类型，1 课程，2：实验
     */
    private Integer type;

    /**
     * 题库ID
     */
    private Long topicId;

    /**
     * 用户选项
     */
    private String topicAnswer;

    /**
     * 状态，1 未删除，0 禁用，-1 删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
