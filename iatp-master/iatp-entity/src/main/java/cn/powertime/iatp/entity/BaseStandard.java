package cn.powertime.iatp.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 知识库内容标准表
 * </p>
 *
 * @author yang xin
 * @since 2019-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseStandard implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 知识库条目ID
     */
    private Long controlId;

    /**
     * 访谈提问
     */
    private String question;

    /**
     * 依据条款内容
     */
    private String demain;

    /**
     * 收集审计证据说明
     */
    private String note;

    /**
     * 存在问题
     */
    private String problem;

    /**
     * 答题岗位
     */
    private Integer anwserRoler;

    /**
     * 风险级别
     */
    private Integer rishLevel;

    /**
     * 风险描述
     */
    private String rish;

    /**
     * 建议
     */
    private String suggest;

    /**
     * 分类：0:管理  1:技术
     */
    private Integer type;

    /**
     * 主体单位
     */
    private String auditUnit;

    /**
     * 依赖其它题的ID号，逗号分割
     */
    private String depend;

}
