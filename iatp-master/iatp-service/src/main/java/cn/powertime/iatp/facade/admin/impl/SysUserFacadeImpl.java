package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.commons.Safelevel;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.SysSafeStrategy;
import cn.powertime.iatp.entity.SysUser;
import cn.powertime.iatp.entity.SysUserRole;
import cn.powertime.iatp.enums.PasswordLevelEnum;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.SysUserFacade;
import cn.powertime.iatp.logging.EnumLogType;
import cn.powertime.iatp.logging.Logging;
import cn.powertime.iatp.service.SysSafeStrategyService;
import cn.powertime.iatp.service.SysUserRoleService;
import cn.powertime.iatp.service.SysUserService;
import cn.powertime.iatp.vo.req.admin.*;
import cn.powertime.iatp.vo.resp.admin.SysUserVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
@Facade
@Order(2)
public class SysUserFacadeImpl implements SysUserFacade {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysSafeStrategyService sysSafeStrategyService;

    @Override
    @Logging(code = "sysUser.add",vars = {"addVo.acount","addVo"},type = EnumLogType.ADD)
    public boolean add(SysUserAddVo addVo,String pwd) {
        if(StringUtils.isNotBlank(addVo.getPhone())){
            Pattern pattern = Pattern.compile("^[1][3,4,5,6,7,8][0-9]{9}$");
            Matcher matcher = pattern.matcher(addVo.getPhone());
            if(!matcher.matches()){
                throw new IatpException("手机号不合法");
            }
        }
        if(StringUtils.isNotBlank(addVo.getEmail())){
            Pattern pattern = Pattern.compile("^[0-9a-z]+\\w*@([0-9a-z]+\\.)+[0-9a-z]+$");
            Matcher matcher = pattern.matcher(addVo.getEmail());
            if(!matcher.matches()){
                throw new IatpException("邮箱格式不正确");
            }
        }
        boolean b = checkUserNameOnly(addVo.getAcount(), null);
        if (b) {
            throw new IatpException("账号已经存在");
        }
        //根据安全策略验证密码长度及强度是否满足要求
        QueryWrapper<SysSafeStrategy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constants.STATUS_NORMAL);
        SysSafeStrategy strategy = sysSafeStrategyService.getOne(queryWrapper);
        if (strategy != null) {
            int minPassword = strategy.getMinPwdLength();
            if (pwd.length() < minPassword) {
                throw new IatpException("您的密码长度小于系统设定，系统设定最小长度为“" + minPassword + "”");
            }
            int passwordStrength = strategy.getPwdIntensity();
            int score = Safelevel.score(pwd);
            if (score < passwordStrength) {
                throw new IatpException("您的密码强度小于系统设定，系统设定密码强度为“"+ PasswordLevelEnum.getPasswordLevelEnum(passwordStrength).getNotes()+"”，"+PasswordLevelEnum.getPasswordLevelEnum(passwordStrength).getDesc());
            }
        }
        SysUser user = new SysUser();
        BeanUtils.copyProperties(addVo, user);
        //user.setPassword(new SCryptPasswordEncoder().encode(addVo.getPassword()));
        user.setId(IdWorker.getId());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setModPasswdDate(LocalDateTime.now());
        return sysUserService.ownSave(user);
    }

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
    @Logging(code = "sysUser.edit",vars = {"editVo.acount","editVo"},type = EnumLogType.UPDATE)
    public boolean edit(SysUserEditVo editVo) {
        if(StringUtils.isNotBlank(editVo.getPhone())){
            Pattern pattern = Pattern.compile("^[1][3,4,5,6,7,8][0-9]{9}$");
            Matcher matcher = pattern.matcher(editVo.getPhone());
            if(!matcher.matches()){
                throw new IatpException("手机号不合法");
            }
        }
        if(StringUtils.isNotBlank(editVo.getEmail())){
            Pattern pattern = Pattern.compile("^[0-9a-z]+\\w*@([0-9a-z]+\\.)+[0-9a-z]+$");
            Matcher matcher = pattern.matcher(editVo.getEmail());
            if(!matcher.matches()){
                throw new IatpException("邮箱格式不正确");
            }
        }
        boolean b = checkUserNameOnly(editVo.getAcount(), editVo.getId());
        if (b) {
            throw new IatpException("账号已存在");
        }
        SysUser user = new SysUser();
        BeanUtils.copyProperties(editVo, user);
        user.setUpdateTime(LocalDateTime.now());
        return sysUserService.edit(user);
    }

    @Override
    public boolean accountUpdate(SysUserUpdateReqVo editVo) {
        if(StringUtils.isNotBlank(editVo.getPhone())){
            Pattern pattern = Pattern.compile("^[1][3,4,5,6,7,8][0-9]{9}$");
            Matcher matcher = pattern.matcher(editVo.getPhone());
            if(!matcher.matches()){
                throw new IatpException("手机号不合法");
            }
        }
        if(StringUtils.isNotBlank(editVo.getEmail())){
            Pattern pattern = Pattern.compile("^[0-9a-z]+\\w*@([0-9a-z]+\\.)+[0-9a-z]+$");
            Matcher matcher = pattern.matcher(editVo.getEmail());
            if(!matcher.matches()){
                throw new IatpException("邮箱格式不正确");
            }
        }
        boolean b = checkUserNameOnly(editVo.getAcount(), editVo.getId());
        if (b) {
            throw new IatpException("账号已存在");
        }

        SysUser user = new SysUser();
        BeanUtils.copyProperties(editVo, user);
        user.setUpdateTime(LocalDateTime.now());
        return sysUserService.edit(user);
    }

    @Override
    @Logging(code = "sysUser.del",vars = {"","id"},type = EnumLogType.DEL)
    public boolean del(Long id) {
        SysUser user = sysUserService.getById(id);
        if (user.getDel() == Constants.NO_ALLOW_DEL) {
            throw new IatpException("当前用户不允许删除");
        }
        user.setStatus(Constants.STATUS_DEL);
        return sysUserService.del(user);
    }

    @Override
    public Page<SysUserVo> list(ParamPageVo<SysUserListVo> vo) {
        Page<SysUserVo> page = new Page<>(vo.getPage().getCurrent(), vo.getPage().getSize());
        return sysUserService.selectPage(page, vo.getParams());

    }

    @Override
    @Logging(code = "sysUserPwd.edit",vars = {"vo.id","vo"},type = EnumLogType.UPDATE)
    public boolean updatePwd(SysUserUpdatePwdVo vo,String newPwd) {
        SysUser sysUser = sysUserService.getById(vo.getId());
        boolean checkOld = new SCryptPasswordEncoder().matches(vo.getOldPassword(), sysUser.getPassword());
        if (!checkOld) {
            throw new IatpException("旧密码输入不正确");
        }
        boolean checkNew = new SCryptPasswordEncoder().matches(newPwd, sysUser.getPassword());
        if (checkNew) {
            throw new IatpException("旧密码和新密码相同");
        }
        //根据安全策略验证密码长度及强度是否满足要求
        QueryWrapper<SysSafeStrategy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constants.STATUS_NORMAL);
        SysSafeStrategy strategy = sysSafeStrategyService.getOne(queryWrapper);
        if (strategy != null) {
            int minPassword = strategy.getMinPwdLength();
            if (newPwd.length() < minPassword) {
                throw new IatpException("您的密码长度小于系统设定，系统设定最小长度为“" + minPassword + "”");
            }
            int passwordStrength = strategy.getPwdIntensity();
            int score = Safelevel.score(newPwd);
            if (score < passwordStrength) {
                throw new IatpException("您的密码强度小于系统设定，系统设定密码强度为“"+ PasswordLevelEnum.getPasswordLevelEnum(passwordStrength).getNotes()+"”，"+PasswordLevelEnum.getPasswordLevelEnum(passwordStrength).getDesc());
            }
        }
        sysUser.setPassword(vo.getNewPassword());
        sysUser.setModPasswdDate(LocalDateTime.now());

        if(sysUser.getStatus() == Constants.STATUS_RESET_PWD
                || sysUser.getStatus() == Constants.STATUS_PWD_EXPIRE) {
            sysUser.setStatus(Constants.STATUS_NORMAL);
        }
        return sysUserService.edit(sysUser);
    }

    @Override
    @Logging(code = "sysUserPwd.edit",vars = {"vo.id","vo"},type = EnumLogType.UPDATE)
    public boolean resetPwd(SysUserResetPwdReqVo vo) {
        SysUser sysUser = sysUserService.getById(vo.getId());
        if(sysUser == null) {
            throw new IatpException("用户不存在");
        }
        sysUser.setPassword(vo.getNewPassword());
        sysUser.setModPasswdDate(LocalDateTime.now());
        sysUser.setStatus(Constants.STATUS_RESET_PWD);
        return sysUserService.edit(sysUser);
    }

    @Override
    public SysUser details(Long id) {
        return sysUserService.findById(id);
    }

    @Override
    public boolean auth(SysUserAuthVo authVo) {
        //删除用户角色
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", authVo.getId());
        sysUserRoleService.remove(wrapper);

        //封装用户角色
        String roleIds = authVo.getRoleIds();
        List<String> resIdList = Splitter.on(",").splitToList(roleIds);
        if (null == resIdList || resIdList.isEmpty()) {
            throw new IatpException("角色信息为空");
        }
        List<SysUserRole> sysRoleResList = resIdList.stream().map(item -> {
            SysUserRole roleRes = new SysUserRole();
            roleRes.setId(IdWorker.getId());
            roleRes.setRoleId(Long.valueOf(item));
            roleRes.setUserId(authVo.getId());
            return roleRes;
        }).collect(Collectors.toList());
        return sysUserRoleService.saveBatch(sysRoleResList);
    }

    @Override
    @Logging(code = "sysUser.del",vars = {"","strings"},type = EnumLogType.DEL)
    public boolean batchDel(List<String> strings) {
        return sysUserService.batchDel(strings);
    }
}
