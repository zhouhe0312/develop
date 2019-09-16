package cn.powertime.iatp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 知识表
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseKnowledgeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类别
     */
    private Integer category;

    /**
     * 小类
     */
    @TableField(value = "class")
    private Integer classes;

    /**
     * 所属领域
     */
    private Integer fields;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 文号
     */
    private String version;

    /**
     * 状态，1 未删除，0 禁用，-1 删除
     */
    private Integer status;

    /**
     * 发布日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate releaseDate;

    /**
     * 废止日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate discardDate;

    /**
     * 发布单位
     */
    private String issued;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 所属项目
     */
    private Integer projectId;

    /**
     * 文件名
     */
    private String filename;


}
