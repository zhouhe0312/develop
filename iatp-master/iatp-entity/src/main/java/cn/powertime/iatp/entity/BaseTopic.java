package cn.powertime.iatp.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 题库表
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseTopic implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 题库类型ID
     */
    private Long typeId;

    /**
     * 试题题干
     */
    private String topicStem;

    /**
     * 试题答案
     */
    private String topicAnswer;

    private Integer scoreValue;

    /**
     * A选项
     */
    private String optionA;

    /**
     * B选项
     */
    private String optionB;

    /**
     * C选项
     */
    private String optionC;

    /**
     * D选项
     */
    private String optionD;

    /**
     * 试题解析
     */
    private String topicAnalysis;

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
