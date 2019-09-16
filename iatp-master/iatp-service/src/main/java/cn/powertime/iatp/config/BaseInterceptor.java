package cn.powertime.iatp.config;

import cn.powertime.iatp.authres.security.SecurityUtils;
import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.exception.PasswordResetExpireException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZYW
 */
public class BaseInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();

        if(StringUtils.contains(uri,"/sysuser/updatePwd")) {
            return true;
        }

        Integer status = SecurityUtils.getStatus();
        if(status == Constants.STATUS_RESET_PWD) {
            throw new PasswordResetExpireException("用户密码被重置，请修改密码！");
        } else if(status == Constants.STATUS_PWD_EXPIRE) {
            throw new PasswordResetExpireException("用户密码已过期，请修改密码！");
        } else if(status == Constants.STATUS_DEL) {
            throw new IatpException("用户信息不存在！");
        }

        return true;
    }
}
