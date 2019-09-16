package cn.powertime.iatp.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 知识内容表
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseKnowledgeContent implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 资料名称
     */
    private String name;

    /**
     * 父id
     */
    private Long father;

    /**
     * 内容
     */
    private String content;

    /**
     * 资料ID
     */
    private Long rootId;

    /**
     * 同级序号
     */
    private Float idx;

    /**
     * 图片文件名
     */
    private String filename;

}
