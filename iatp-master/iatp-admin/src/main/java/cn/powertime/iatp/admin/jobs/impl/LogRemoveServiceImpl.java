package cn.powertime.iatp.admin.jobs.impl;

import cn.powertime.iatp.admin.jobs.LogRemoveService;
import cn.powertime.iatp.entity.SysLog;
import cn.powertime.iatp.facade.admin.SysLogFacade;
import cn.powertime.iatp.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LogRemoveServiceImpl implements LogRemoveService {

    @Autowired
    private SysLogFacade sysLogFacade;

    @Override
    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨1点执行一次
    public void logRemove() {
        System.out.println("--------------------------------日志定时器开始执行"+ LocalDateTime.now()+"--------------------------------------");
        List<SysLog> sysLogs = sysLogFacade.selectListByDay();
        if(CollectionUtils.isNotEmpty(sysLogs)){
            List<Long> ids = sysLogs.stream().map(item -> item.getId()).collect(Collectors.toList());
            sysLogFacade.delete(ids);
        }
        System.out.println("--------------------------------日志定时器执行完成"+ LocalDateTime.now()+"--------------------------------------");
    }

}
