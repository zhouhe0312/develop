package cn.powertime.iatp.authserver.service.impl;

import cn.powertime.iatp.authserver.domain.SysSafeStrategy;
import cn.powertime.iatp.authserver.mapper.SysSafeStrategyMapper;
import cn.powertime.iatp.authserver.service.SysSafeStrategyService;
import cn.powertime.iatp.commons.Constants;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 安全策略表 服务实现类
 * </p>
 *
 * @author yang xin
 * @since 2019-06-18
 */
@Service
public class SysSafeStrategyServiceImpl extends ServiceImpl<SysSafeStrategyMapper, SysSafeStrategy> implements SysSafeStrategyService {

    @Override
    public SysSafeStrategy getOne() {
        QueryWrapper<SysSafeStrategy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constants.STATUS_NORMAL);
        return getOne(queryWrapper);
    }

}
