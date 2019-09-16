package cn.powertime.iatp.facade.web.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.commons.Safelevel;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.SysSafeStrategy;
import cn.powertime.iatp.entity.SysUser;
import cn.powertime.iatp.enums.PasswordLevelEnum;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.web.UserFacade;
import cn.powertime.iatp.service.SysSafeStrategyService;
import cn.powertime.iatp.service.SysUserService;
import cn.powertime.iatp.vo.req.web.UserRegisterVo;
import cn.powertime.iatp.vo.req.web.UserUpdatePwdVo;
import cn.powertime.iatp.vo.req.web.UserUpdateVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysSafeStrategyService sysSafeStrategyService;

    @Override
    public boolean checkUserNameOnly(String userName, Long id) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("acount", userName);
        wrapper.ne("status", Constants.STATUS_DEL);
        if (null != id) {
            wrapper.ne("id", id);
        }
        return sysUserService.count(wrapper) > 0;
    }

    @Override
    public boolean register(UserRegisterVo registerVo) {
        if (!StringUtils.equals(registerVo.getPassword(), registerVo.getConfirmPassword())) {
            throw new IatpException("两次输入密码不一致");
        }
        boolean b = checkUserNameOnly(registerVo.getAcount(), null);
        if (b) {
            throw new IatpException("账号已经存在");
        }
        //根据安全策略验证密码长度及强度是否满足要求
        QueryWrapper<SysSafeStrategy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constants.STATUS_NORMAL);
        SysSafeStrategy strategy = sysSafeStrategyService.getOne(queryWrapper);
        if (strategy != null) {
            int minPassword = strategy.getMinPwdLength();
            if (registerVo.getPassword().length() < minPassword) {
                throw new IatpException("您的密码长度小于系统设定，系统设定最小长度为“" + minPassword + "”");
            }
            int passwordStrength = strategy.getPwdIntensity();
            int score = Safelevel.score(registerVo.getPassword());
            System.out.println(score);
            if (score < passwordStrength) {
                throw new IatpException("您的密码强度小于系统设定，系统设定密码强度为“" + PasswordLevelEnum.getPasswordLevelEnum(passwordStrength).getNotes() + "”，" + PasswordLevelEnum.getPasswordLevelEnum(passwordStrength).getDesc());
            }
        }
        SysUser user = new SysUser();
        BeanUtils.copyProperties(registerVo, user);
        user.setPassword(new SCryptPasswordEncoder().encode(registerVo.getPassword()));
        user.setId(IdWorker.getId());
        user.setType(2);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setModPasswdDate(LocalDateTime.now());
        return sysUserService.ownSave(user);
    }

    @Override
    public SysUser details(Long id) {
        return sysUserService.findById(id);
    }

    @Override
    public boolean updatePwd(UserUpdatePwdVo vo) {
        if (!StringUtils.equals(vo.getNewPassword(), vo.getRepPassword())) {
            throw new IatpException("两次输入密码不一致");
        }
        SysUser sysUser = sysUserService.getById(vo.getId());
        if (sysUser == null) {
            throw new IatpException("账号不存在！");
        }
        boolean checkOld = new SCryptPasswordEncoder().matches(vo.getOldPassword(), sysUser.getPassword());
        if (!checkOld) {
            throw new IatpException("旧密码输入不正确");
        }
        boolean checkNew = new SCryptPasswordEncoder().matches(vo.getNewPassword(), sysUser.getPassword());
        if (checkNew) {
            throw new IatpException("旧密码和新密码相同");
        }
        //根据安全策略验证密码长度及强度是否满足要求
        QueryWrapper<SysSafeStrategy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constants.STATUS_NORMAL);
        SysSafeStrategy strategy = sysSafeStrategyService.getOne(queryWrapper);
        if (strategy != null) {
            int minPassword = strategy.getMinPwdLength();
            if (vo.getNewPassword().length() < minPassword) {
                throw new IatpException("您的密码长度小于系统设定，系统设定最小长度为“" + minPassword + "”");
            }
            int passwordStrength = strategy.getPwdIntensity();
            int score = Safelevel.score(vo.getNewPassword());
            if (score < passwordStrength) {
                throw new IatpException("您的密码强度小于系统设定，系统设定密码强度为“"+ PasswordLevelEnum.getPasswordLevelEnum(passwordStrength).getNotes()+"”，"+PasswordLevelEnum.getPasswordLevelEnum(passwordStrength).getDesc());
            }
        }
        sysUser.setPassword(new SCryptPasswordEncoder().encode(vo.getNewPassword()));
        sysUser.setModPasswdDate(LocalDateTime.now());
        return sysUserService.edit(sysUser);
    }

    @Override
    public boolean updateUser(UserUpdateVo vo) {
        if(StringUtils.isNotBlank(vo.getPhone())){
            Pattern pattern = Pattern.compile("^[1][3,4,5,6,7,8][0-9]{9}$");
            Matcher matcher = pattern.matcher(vo.getPhone());
            if(!matcher.matches()){
                throw new IatpException("手机号不合法");
            }
        }
        if(StringUtils.isNotBlank(vo.getEmail())){
            Pattern pattern = Pattern.compile("^[0-9a-z]+\\w*@([0-9a-z]+\\.)+[0-9a-z]+$");
            Matcher matcher = pattern.matcher(vo.getEmail());
            if(!matcher.matches()){
                throw new IatpException("邮箱格式不正确");
            }
        }
        SysUser user = new SysUser();
        BeanUtils.copyProperties(vo, user);
        user.setUpdateTime(LocalDateTime.now());
        return sysUserService.edit(user);
    }

    @Override
    public boolean passwordCheck(String password) {
        //根据安全策略验证密码长度及强度是否满足要求
        QueryWrapper<SysSafeStrategy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constants.STATUS_NORMAL);
        SysSafeStrategy strategy = sysSafeStrategyService.getOne(queryWrapper);
        if (strategy != null) {
            int minPassword = strategy.getMinPwdLength();
            if (password.length() < minPassword) {
                throw new IatpException("您的密码长度小于系统设定，系统设定最小长度为“" + minPassword + "”");
            }
            int passwordStrength = strategy.getPwdIntensity();
            int score = Safelevel.score(password);
            System.out.println(score);
            if (score < passwordStrength) {
                throw new IatpException("您的密码强度小于系统设定，系统设定密码强度为“" + PasswordLevelEnum.getPasswordLevelEnum(passwordStrength).getNotes() + "”，" + PasswordLevelEnum.getPasswordLevelEnum(passwordStrength).getDesc());
            }
        }
        return true;
    }

    @Override
    public String passwordTips() {
        //根据安全策略验证密码长度及强度是否满足要求
        QueryWrapper<SysSafeStrategy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constants.STATUS_NORMAL);
        SysSafeStrategy strategy = sysSafeStrategyService.getOne(queryWrapper);
        if (strategy != null) {
            int passwordStrength = strategy.getPwdIntensity();
            return PasswordLevelEnum.getPasswordLevelEnum(passwordStrength).getDesc();
        }
        return null;
    }

}
