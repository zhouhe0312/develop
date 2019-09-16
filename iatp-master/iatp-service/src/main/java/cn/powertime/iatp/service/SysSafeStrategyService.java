package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.SysSafeStrategy;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 安全策略 服务类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
public interface SysSafeStrategyService extends IService<SysSafeStrategy> {

    SysSafeStrategy selectById(Long id);

    boolean ownUpdateById(SysSafeStrategy vo);
}
