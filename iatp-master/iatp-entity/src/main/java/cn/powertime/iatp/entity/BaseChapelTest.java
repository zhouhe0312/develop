package cn.powertime.iatp.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 试卷表
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseChapelTest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 试卷名称
     */
    private String title;

    /**
     * 归属课程/实验ID
     */
    @TableField(value = "course_id",strategy = FieldStrategy.IGNORED)
    private Long courseId;

    /**
     * 归属章节ID
     */
    @TableField(value = "chapter_id",strategy = FieldStrategy.IGNORED)
    private Long chapterId;

    /**
     * 小节ID
     */
    @TableField(value = "subsection",strategy = FieldStrategy.IGNORED)
    private Long subsection;

    /**
     * 考试时长
     */
    private Long paperDuration;

    /**
     * 总分数
     */
    private Integer score;

    /**
     * 归属课程ID
     */
    @TableField(value = "course_one_id",strategy = FieldStrategy.IGNORED)
    private Long courseOneId;

    /**
     * 归属实验ID
     */
    private Long courseTwoId;

    /**
     * 类型，1:课程随堂，2：课程单元，3：课程，4：实验随堂，5：实验单元，6：实验，7：综合
     */
    private Integer testType;

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
