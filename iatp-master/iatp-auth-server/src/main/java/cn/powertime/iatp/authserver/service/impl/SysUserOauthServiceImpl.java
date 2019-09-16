package cn.powertime.iatp.authserver.service.impl;

import cn.powertime.iatp.authserver.domain.SysUser;
import cn.powertime.iatp.authserver.mapper.SysUserOauthMapper;
import cn.powertime.iatp.authserver.service.SysUserOauthService;
import cn.powertime.iatp.commons.AuthResVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : liqi
 * Date: 2018-11-24
 * Time: 10:43
 */
@Slf4j
@Service
public class SysUserOauthServiceImpl extends ServiceImpl<SysUserOauthMapper, SysUser> implements SysUserOauthService {

    @Override
    public AuthResVo getUserAndAuth(Long id) {
        AuthResVo vo = new AuthResVo();
        SysUser sysUser =  getAccountAndStatusById(id);
        log.info("查询用户信息：" + sysUser);
        vo.setUserName(sysUser.getAcount());
        vo.setStatus(sysUser.getStatus());
//        vo.setCustomerId(sysUser.getCustomerId());
        //TODO 后期添加权限
        return vo;
    }

    private SysUser getAccountAndStatusById(Long id) {
        return this.baseMapper.findAccountAndStatusById(id);
    }
}
