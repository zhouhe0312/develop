package cn.powertime.iatp.logging;

import com.google.common.collect.Maps;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author zyw
 * @date 2018/2/6
 */
@Aspect
@Component
@Order(1)
@Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_COMMITTED, noRollbackFor = Exception.class)
public class LoggingAspect extends AbstractLogAspect {

    private Map<String,Object> log = Maps.newHashMap();

    private static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(cn.powertime.iatp.logging.Logging)")
    public Object methodCacheHold(ProceedingJoinPoint joinPoint)
            throws Throwable {
        Logging simpleLog = AspectUtils.getAnnotation(joinPoint, Logging.class);
        Map<String, Object> params = AspectUtils.getMethodParams(joinPoint);
        log.put("desc",getMessage(simpleLog.code(), simpleLog.vars(), params));//对应 日志数据
        log.put("remark",getMessage(simpleLog.code() + ".message",simpleLog.vars(),params));//操作详情
        log.put("type",simpleLog.type().getType());//操作类型
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }


    @AfterReturning("@annotation(cn.powertime.iatp.logging.Logging)")
    public void afterReturning(JoinPoint joinPoint){
        log.put("isSuccess",1);//操作成功
        saveLog(log);
    }

    @AfterThrowing(value = "@annotation(cn.powertime.iatp.logging.Logging)", throwing = "e")
    public void afterThrowing(Throwable e) {
        if(e instanceof Exception){
            log.put("isSuccess",2);//操作失败
            saveLog(log);
        }
    }

}
