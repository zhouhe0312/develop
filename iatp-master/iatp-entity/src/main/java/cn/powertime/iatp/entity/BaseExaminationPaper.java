package cn.powertime.iatp.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 试卷试题表
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseExaminationPaper implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 试卷ID
     */
    private Long testPaperId;

    /**
     * 分值
     */
    private Integer scoreValue;

    /**
     * 题库ID
     */
    private Long topicId;

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
