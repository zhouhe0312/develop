package cn.powertime.iatp.authserver.service.impl;

import cn.powertime.iatp.authserver.domain.SysLog;
import cn.powertime.iatp.authserver.domain.SysUser;
import cn.powertime.iatp.authserver.mapper.SysLogMapper;
import cn.powertime.iatp.authserver.service.SysRoleService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <p>
 * 日志信息表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2018-11-23
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysRoleService {

    @Override
    //@Async
    public void inserLoginLog(SysUser user) {

        user.setPassword("");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SysLog sysLog = new SysLog();
        String ip =getIpAddr(request);
        sysLog.setId(IdWorker.getId());
        sysLog.setAdr(ip);
        sysLog.setDescInfo("登录");
        sysLog.setBackups(1);
        sysLog.setCreateTime(LocalDateTime.now());
        sysLog.setRemark(JSONObject.toJSONString(user));
        sysLog.setType(5);
        sysLog.setCreateUserId(user.getId());
        save(sysLog);
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
