package cn.powertime.iatp.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户考试结果详情表
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseExaminationResultDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 考试结果ID
     */
    private Long resultId;

    /**
     * 试卷ID
     */
    private Long testPaperId;

    /**
     * 试卷试题表ID
     */
    private Long testQuestionsId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户选项
     */
    private String topicAnswer;

    /**
     * 是否正确，1 正确，2：错误
     */
    private Integer isCorrect;

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
