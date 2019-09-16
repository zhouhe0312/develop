package cn.powertime.iatp.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户考试结果表
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
public class BaseExaminationResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 试卷ID
     */
    private Long testPaperId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 得分
     */
    private BigDecimal score;

    /**
     * 考试用时
     */
    private Long examTime;

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
