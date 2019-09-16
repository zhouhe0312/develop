package cn.powertime.iatp.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 课程（实验）章节表
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseChapter implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 上级分类id
     */
    private Long pid;

    /**
     * 类型，1 课程，2：实验
     */
    private Integer type;

    /**
     * 章节（小节）名称
     */
    private String chapterName;

    /**
     * 课程（实验）ID
     */
    private Long courseId;

    /**
     * 描述
     */
    private String des;

    /**
     * 排序
     */
    private Integer sortNum;

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
