package cn.powertime.iatp.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分类表
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseClassify implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 上级分类id
     */
    private Long pid;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类编码
     */
    private String code;
    /**
     * 描述
     */
    private String des;

    /**
     * 分类类型，1：课程，2：实验
     */
    private Integer type;

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
