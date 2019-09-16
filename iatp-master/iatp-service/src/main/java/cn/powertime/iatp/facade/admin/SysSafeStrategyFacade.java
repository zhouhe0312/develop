package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.entity.SysSafeStrategy;
import cn.powertime.iatp.vo.req.admin.SysSafeStrategyEditVo;

/**
 * <p>
 * 安全策略 服务类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
public interface SysSafeStrategyFacade {

    boolean edit(SysSafeStrategyEditVo editVo);

    SysSafeStrategy selectById(Long id);

    SysSafeStrategy getOne();

    /**
     * 密码强度(
     * >= 90: 非常安全(VERY_WEAK)
     * >= 80: 安全(WEAK)
     * >= 70: 非常强(VERY_STRONG)
     * >= 60: 强(STRONG)
     * >= 50: 一般(AVERAGE)
     * >= 25: 弱(WEAK)
     * >= 0:  非常弱(VERY_WEAK)
     * @param password
     * @return
     */
    Integer passwordStrength(String password);
}
