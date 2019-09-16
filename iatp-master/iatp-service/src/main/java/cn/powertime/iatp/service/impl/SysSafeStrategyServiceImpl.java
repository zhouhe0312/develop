package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.SysSafeStrategy;
import cn.powertime.iatp.mapper.SysSafeStrategyMapper;
import cn.powertime.iatp.service.SysSafeStrategyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 安全策略 服务实现类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Service
public class SysSafeStrategyServiceImpl extends ServiceImpl<SysSafeStrategyMapper, SysSafeStrategy> implements SysSafeStrategyService {

    @Override
    public SysSafeStrategy selectById(Long id) {
        return getById(id);
    }

    @Override
    public boolean ownUpdateById(SysSafeStrategy vo) {
        return updateById(vo);
    }
}
