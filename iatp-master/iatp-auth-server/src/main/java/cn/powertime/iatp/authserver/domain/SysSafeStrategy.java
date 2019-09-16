package cn.powertime.iatp.authserver.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 安全策略表
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysSafeStrategy implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 密码强度
     */
    private Integer pwdIntensity;

    /**
     * 允许登陆失败次数
     */
    private Integer loginFailTime;

    /**
     * 密码有效时间（单位：秒）
     */
    private Long pwdValidTime;

    /**
     * 页面失效时间（单位：秒）
     */
    private Long webFailureTime;

    /**
     * 锁定用户登陆时间（单位：秒）
     */
    private Long lockLoginTime;

    /**
     * 日志保留最短时间（单位：天）
     */
    private Integer logRetentionTime;

    /**
     * 密码最小长度
     */
    private Integer minPwdLength;

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
