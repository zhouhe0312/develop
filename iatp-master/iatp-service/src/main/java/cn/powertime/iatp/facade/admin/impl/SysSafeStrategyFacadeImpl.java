package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.commons.Safelevel;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.SysSafeStrategy;
import cn.powertime.iatp.facade.admin.SysSafeStrategyFacade;
import cn.powertime.iatp.service.SysSafeStrategyService;
import cn.powertime.iatp.vo.req.admin.SysSafeStrategyEditVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
@Facade
@Order(2)
public class SysSafeStrategyFacadeImpl implements SysSafeStrategyFacade {

    @Autowired
    private SysSafeStrategyService sysSafeStrategyService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean edit(SysSafeStrategyEditVo editVo) {
        QueryWrapper<SysSafeStrategy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constants.STATUS_NORMAL);
        SysSafeStrategy sysSafeStrategy = sysSafeStrategyService.getOne(queryWrapper);
        if(sysSafeStrategy.getWebFailureTime().longValue() != editVo.getWebFailureTime().longValue()){
            stringRedisTemplate.opsForValue().set("token_expire_in",editVo.getWebFailureTime().toString());
        }
        SysSafeStrategy vo = new SysSafeStrategy();
        BeanUtils.copyProperties(editVo,vo);
        vo.setUpdateTime(LocalDateTime.now());
        return sysSafeStrategyService.ownUpdateById(vo);
    }

    @Override
    public SysSafeStrategy selectById(Long id) {
        return sysSafeStrategyService.selectById(id);
    }

    @Override
    public SysSafeStrategy getOne() {
        QueryWrapper<SysSafeStrategy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constants.STATUS_NORMAL);
        return sysSafeStrategyService.getOne(queryWrapper);
    }

    @Override
    public Integer passwordStrength(String password) {
        return Safelevel.score(password);
    }

}
