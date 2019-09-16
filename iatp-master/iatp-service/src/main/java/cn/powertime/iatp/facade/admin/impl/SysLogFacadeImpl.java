package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.SysLog;
import cn.powertime.iatp.entity.SysSafeStrategy;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.SysLogFacade;
import cn.powertime.iatp.facade.admin.SysSafeStrategyFacade;
import cn.powertime.iatp.service.SysLogService;
import cn.powertime.iatp.utils.CollectionUtils;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.SysLogListVo;
import cn.powertime.iatp.vo.resp.admin.SysLogExportVo;
import cn.powertime.iatp.vo.resp.admin.SysLogPageListVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Facade
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
public class SysLogFacadeImpl implements SysLogFacade {

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    SysSafeStrategyFacade sysSafeStrategyFacade;

    @Override
    public IPage<SysLogPageListVo> list(ParamPageVo<SysLogListVo> vo) {
        Page<SysLogPageListVo> page = new Page<>(vo.getPage().getCurrent(),vo.getPage().getSize());
        return sysLogService.selectPage(page,vo.getParams());
    }

    @Override
    public SysLog findById(Long id) {
        return sysLogService.findById(id);
    }

    @Override
    public List<SysLogExportVo> export(SysLogListVo vo) {
        List<SysLogExportVo> list = sysLogService.export(vo);
        if(CollectionUtils.isEmpty(list)){
            throw new IatpException("暂无导出数据！", HttpStatus.NOT_ACCEPTABLE.value());
        }
        List<SysLog> sysLogs = list.stream().map(item -> {
            SysLog log = new SysLog();
            log.setId(item.getId());
            log.setBackups(2);
            return log;
        }).collect(Collectors.toList());
        sysLogService.updateBatchById(sysLogs);
        return list;
    }

    @Override
    public boolean delete(List<Long> ids) {
        return sysLogService.delete(ids);
    }

    @Override
    public List<SysLog> selectListByDay() {
        SysSafeStrategy strategy = sysSafeStrategyFacade.getOne();
        if(strategy != null){
            if(strategy.getLogRetentionTime() > 0) {
                QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("backups",2);
                queryWrapper.apply("create_time <= DATE_SUB(NOW(), INTERVAL " + strategy.getLogRetentionTime() + " DAY)");
                return sysLogService.list(queryWrapper);
            }
        }
        return null;
    }

}
