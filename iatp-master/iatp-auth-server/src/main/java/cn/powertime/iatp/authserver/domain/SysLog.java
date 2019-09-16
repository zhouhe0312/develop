package cn.powertime.iatp.authserver.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 日志信息表
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志信息表主键
     */
    private Long id;

    /**
     * 日志类型（1:增，2:删，3:改，4:查）
     */
    private Integer type;

    /**
     * 日志说明（日志数据）
     */
    private String descInfo;

    /**
     * 操作详情
     */
    private String remark;

    /**
     * 操作人IP地址
     */
    private String adr;

    /**
     * 日志备份状态，1：未备份，2：已备份
     */
    private Integer backups;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建用户ID
     */
    private Long createUserId;


}
