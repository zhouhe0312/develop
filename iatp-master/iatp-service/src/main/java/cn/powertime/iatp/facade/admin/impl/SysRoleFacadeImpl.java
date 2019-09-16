package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.SysRole;
import cn.powertime.iatp.entity.SysRoleRes;
import cn.powertime.iatp.entity.SysUserRole;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.SysRoleFacade;
import cn.powertime.iatp.logging.EnumLogType;
import cn.powertime.iatp.logging.Logging;
import cn.powertime.iatp.service.SysRoleResService;
import cn.powertime.iatp.service.SysRoleService;
import cn.powertime.iatp.service.SysUserRoleService;
import cn.powertime.iatp.vo.req.admin.*;
import cn.powertime.iatp.vo.resp.admin.SysRolePageListVo;
import cn.powertime.iatp.vo.resp.admin.UserRoleVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Splitter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : liqi
 * Date: 2019-04-17
 * Time: 20:24
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
@Facade
@Order(2)
public class SysRoleFacadeImpl implements SysRoleFacade {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleResService sysRoleResService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public boolean checkNameAndCodeOnly(int type, String value, Long id) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        if (type != 1 && type != 2) {
            throw new IatpException("类型输入有误，只能为1或2");
        }
        //1：名称 2：编码
        if (type == 1) {
            wrapper.eq("name", value);
        }
        if (type == 2) {
            wrapper.eq("code", value);
        }
        wrapper.ne("status", Constants.STATUS_DEL);
        if (id != null) {
            wrapper.ne("id", id);
        }
        return sysRoleService.count(wrapper) > 0;
    }

    @Override
    @Logging(code = "role.add", vars = {"addVo.name", "addVo"}, type = EnumLogType.ADD)
    public boolean add(SysRoleAddVo addVo) {
        boolean name = checkNameAndCodeOnly(1, addVo.getName(), null);
        if (name) {
            throw new IatpException("名称已经存在");
        }
        boolean code = checkNameAndCodeOnly(2, addVo.getCode(), null);
        if (code) {
            throw new IatpException("编码已经存在");
        }
        SysRole role = new SysRole();
        BeanUtils.copyProperties(addVo, role);
        role.setId(IdWorker.getId());
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        return sysRoleService.ownSave(role);
    }

    @Override
    @Logging(code = "role.edit", vars = {"editVo.name", "editVo"}, type = EnumLogType.UPDATE)
    public boolean edit(SysRoleEditVo editVo) {
        boolean name = checkNameAndCodeOnly(1, editVo.getName(), editVo.getId());
        if (name) {
            throw new IatpException("名称已经存在");
        }
        boolean code = checkNameAndCodeOnly(2, editVo.getCode(), editVo.getId());
        if (code) {
            throw new IatpException("编码已经存在");
        }
        SysRole role = new SysRole();
        BeanUtils.copyProperties(editVo, role);
        role.setUpdateTime(LocalDateTime.now());
        return sysRoleService.ownUpdateById(role);
    }

    @Override
    public boolean del(Long id) {
        SysRole sysRole = sysRoleService.getById(id);
        if (sysRole.getDel() == Constants.NO_ALLOW_DEL) {
            throw new IatpException("当前角色不允许删除");
        }
        QueryWrapper<SysRoleRes> sysRoleMenuQueryWrapper = new QueryWrapper<>();
        sysRoleMenuQueryWrapper.eq("role_id", id);
        Integer count1 = sysRoleResService.count(sysRoleMenuQueryWrapper);
        if (count1 > 0) {
            throw new IatpException("角色被菜单引用不能删除");
        }
        QueryWrapper<SysUserRole> sysRoleOperatorQueryWrapper = new QueryWrapper<>();
        sysRoleOperatorQueryWrapper.eq("role_id", id);
        Integer count2 = sysUserRoleService.count(sysRoleOperatorQueryWrapper);
        if (count2 > 0) {
            throw new IatpException("角色被操作员引用不能删除");
        }

        sysRole.setStatus(Constants.STATUS_DEL);
        return sysRoleService.del(sysRole);
    }

    @Override
    public IPage<SysRolePageListVo> list(ParamPageVo<SysRoleListVo> vo) {
        Page<SysRole> page = new Page<>(vo.getPage().getCurrent(), vo.getPage().getSize());
        return sysRoleService.selectPage(page, vo.getParams().getName(), vo.getParams().getCode());
    }

    @Override
    public List<SysRole> listAll() {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("status", Constants.STATUS_NORMAL);
        return sysRoleService.list(wrapper);
    }

    @Override
    public SysRole selectById(Long id) {
        return sysRoleService.getById(id);
    }

    @Override
    public boolean auth(SysRoleAuthVo authVo) {
        //删除之前的资源
        QueryWrapper<SysRoleRes> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", authVo.getId());
        sysRoleResService.remove(wrapper);
        //封装角色资源
        String resIds = authVo.getResIds();
        List<String> resIdList = Splitter.on(",").splitToList(resIds);
        if (null == resIdList || resIdList.isEmpty()) {
            throw new IatpException("资源信息为空");
        }
        List<SysRoleRes> sysRoleResList = resIdList.stream().map(item -> {
            SysRoleRes roleRes = new SysRoleRes();
            roleRes.setId(IdWorker.getId());
            roleRes.setResId(Long.valueOf(item));
            roleRes.setRoleId(authVo.getId());
            return roleRes;
        }).collect(Collectors.toList());
        return sysRoleResService.saveBatch(sysRoleResList);
    }

    @Override
    @Logging(code = "role.del", vars = {"", "strings"}, type = EnumLogType.DEL)
    public boolean batchDel(List<String> strings) {
        return sysRoleService.batchDel(strings);
    }

    @Override
    public List<UserRoleVo> userRole(Long uid) {
        List<SysRole> list = listAll();
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", uid);
        List<SysUserRole> sysUserRoleList = sysUserRoleService.list(queryWrapper);
        Set<Long> roles = sysUserRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
        List<UserRoleVo> userRoleVoList = list.stream().map(item -> {
            UserRoleVo userRoleVo = new UserRoleVo();
            userRoleVo.setName(item.getName());
            userRoleVo.setRoleId(item.getId());
            userRoleVo.setChecked(false);
            if (roles.contains(item.getId())) {
                userRoleVo.setChecked(true);
            }
            return userRoleVo;
        }).collect(Collectors.toList());
        return userRoleVoList;
    }
}
