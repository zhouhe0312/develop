package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.BaseStandard;
import cn.powertime.iatp.facade.admin.BaseStandardFacade;
import cn.powertime.iatp.logging.EnumLogType;
import cn.powertime.iatp.logging.Logging;
import cn.powertime.iatp.service.BaseStandardService;
import cn.powertime.iatp.vo.req.admin.BaseStandardAddVo;
import cn.powertime.iatp.vo.req.admin.BaseStandardEditVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 知识库内容标准表 服务实现类
 * </p>
 *
 * @author yang xin
 * @since 2019-07-04
 */
@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
@Order(2)
public class BaseStandardFacadeImpl implements BaseStandardFacade {

    @Autowired
    private BaseStandardService baseStandardService;

    @Override
    @Logging(code = "standard.add",vars = {"vo.question","vo"},type = EnumLogType.ADD)
    public boolean add(BaseStandardAddVo vo) {
        BaseStandard standard = new BaseStandard();
        BeanUtils.copyProperties(vo, standard);
        standard.setId(IdWorker.getId());
        return baseStandardService.add(standard);
    }

    @Override
    @Logging(code = "standard.edit",vars = {"vo.question","vo"},type = EnumLogType.UPDATE)
    public boolean edit(BaseStandardEditVo vo) {
        BaseStandard standard = new BaseStandard();
        BeanUtils.copyProperties(vo, standard);
        return baseStandardService.edit(standard);
    }

    @Override
    public List<BaseStandard> list(Long controlId) {
        QueryWrapper<BaseStandard> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("control_id", controlId);
        List<BaseStandard> list = baseStandardService.list(queryWrapper);
        return list;
    }

    @Override
    @Logging(code = "standard.del",vars = {"","strings"},type = EnumLogType.DEL)
    public boolean batchDel(List<String> strings) {
        return baseStandardService.batchDel(strings);
    }

}
