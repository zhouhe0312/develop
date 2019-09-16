package cn.powertime.iatp.authserver.service;


import cn.powertime.iatp.authserver.domain.SysUser;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author liqi
 * @since 2018-11-23
 */
public interface SysRoleService{

    void inserLoginLog(SysUser user);
}
