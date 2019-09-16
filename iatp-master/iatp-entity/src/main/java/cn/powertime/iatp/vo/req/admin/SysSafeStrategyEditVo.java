package cn.powertime.iatp.vo.req.admin;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

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
public class SysSafeStrategyEditVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 密码强度
     */
    private Integer pwdIntensity;

    /**
     * 允许登陆失败次数
     */
    @Min(value = 1, message = "允许登陆失败次数最小值是1次")
    @Max(value = 10, message = "允许登陆失败次数最大值是10次")
    private Integer loginFailTime;

    /**
     * 密码有效时间（单位：天）
     */
    @Min(value = 30, message = "密码有效时间最小值是30天")
    @Max(value = 180, message = "密码有效时间最大值是180天")
    private Long pwdValidTime;

    /**
     * 页面失效时间（单位：秒）
     */
    @Min(value = 1800, message = "页面失效时间最小值是1800秒")//30分钟
    @Max(value = 86400, message = "页面失效时间最大值是86400秒")//24小时
    private Long webFailureTime;

    /**
     * 锁定用户登陆时间（单位：秒）
     */
    @Min(value = 180, message = "锁定用户登陆时间最小值是180秒")//3分钟
    @Max(value = 86400, message = "锁定用户登陆时间最大值是1800秒")//30分钟
    private String lockLoginTime;

    /**
     * 日志保留最短时间（单位：天）
     */
    @Min(value = 1, message = "日志保留最短时间最小值是1天")
    @Max(value = 60, message = "日志保留最短时间最大值是60天")
    private Integer logRetentionTime;

    /**
     * 密码最小长度
     */
    @Min(value = 4, message = "密码最小长度最小值值是4")
    @Max(value = 20, message = "密码最小长度最大值是值20天")
    private Integer minPwdLength;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
