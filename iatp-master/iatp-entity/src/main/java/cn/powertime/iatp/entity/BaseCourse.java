package cn.powertime.iatp.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程（实验）表
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 类型，1 课程，2：实验
     */
    private Integer type;

    /**
     * 分类ID
     */
    private Long classifyId;
    /**
     * 分类父ID
     */
    private Long classifyPid;

    /**
     * 课程（实验）名称
     */
    private String courseName;

    /**
     * 课程（实验）介绍
     */
    private String introduce;

    /**
     * 浏览量
     */
    private Long courseWarePv;

    /**
     * 评分
     */
    private Double courseWaresCore;

    /**
     * 课程（实验）封面
     */
    private Long fileId;

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
