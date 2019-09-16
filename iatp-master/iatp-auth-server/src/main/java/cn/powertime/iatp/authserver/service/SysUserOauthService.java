package cn.powertime.iatp.authserver.service;

import cn.powertime.iatp.commons.AuthResVo;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : liqi
 * Date: 2018-11-24
 * Time: 10:29
 */
public interface SysUserOauthService {

    AuthResVo getUserAndAuth(Long id);
}
