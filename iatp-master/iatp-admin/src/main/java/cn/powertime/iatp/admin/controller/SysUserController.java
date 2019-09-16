package cn.powertime.iatp.admin.controller;

import cn.powertime.iatp.authres.security.SecurityUtils;
import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.entity.SysUser;
import cn.powertime.iatp.enums.HttpStatus;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.SysUserFacade;
import cn.powertime.iatp.vo.req.admin.*;
import cn.powertime.iatp.vo.resp.admin.SysUserVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Splitter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liqi
 * @Description: pc端用户管理模块
 * @date 2019/4/18
 */
@RestController
@RequestMapping(value = "/sysuser")
@Api(value = "/sysuser", tags = "系统用户接口", produces = MediaType.ALL_VALUE)
public class SysUserController extends BaseController {

    @Autowired
    private SysUserFacade sysUserFacade;

    @ApiOperation(value = "验证账号唯一性", notes = "")
    @PostMapping("/checkUserNameOnly")
    public Object checkOnly(String acount, Long id) {
        boolean b = sysUserFacade.checkUserNameOnly(acount, id);
        if (b) {
            return error(HttpStatus.CHECK_FAIL, null, "账号已存在");
        }
        return success("账号可用");
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增系统用户", notes = "")
    public Object add(@Validated @RequestBody SysUserAddVo addVo) {
        String password = addVo.getPassword();
        if (!StringUtils.equals(password, addVo.getRepeatPassword())) {
            throw new IatpException("两次输入密码不一致");
        }
        addVo.setPassword(new SCryptPasswordEncoder().encode(addVo.getPassword()));
        addVo.setRepeatPassword("");
        boolean b = sysUserFacade.add(addVo, password);
        if (b) {
            return success("新增用户成功");
        }
        return error("新增用户失败");
    }

    @PostMapping("/edit")
    @ApiOperation(value = "修改系统用户", notes = "")
    public Object edit(@Validated @RequestBody SysUserEditVo editVo) {
        boolean b = sysUserFacade.edit(editVo);
        if (b) {
            return success("修改用户成功");
        }
        return error("修改用户失败");
    }

    @ApiOperation(value = "删除系统用户", notes = "")
    @PostMapping("/del")
    public Object del(Long id) {
        boolean b = sysUserFacade.del(id);
        if (b) {
            return success("删除用户成功");
        }
        return error("删除用户失败");
    }

    @ApiOperation(value = "用户分页列表", notes = "")
    @PostMapping(value = "/list")
    public Object list(@Validated @RequestBody ParamPageVo<SysUserListVo> vo) {
        Page<SysUserVo> list = sysUserFacade.list(vo);
        return success(list);
    }

    @ApiOperation(value = "修改密码", notes = "")
    @PostMapping(value = "/updatePwd")
    public Object updatePwd(@Validated @RequestBody SysUserUpdatePwdVo vo) {
        String newPwd = vo.getNewPassword();
        if (!StringUtils.equals(newPwd, vo.getRepPassword())) {
            throw new IatpException("两次输入密码不一致");
        }
        vo.setNewPassword(new SCryptPasswordEncoder().encode(vo.getNewPassword()));
        vo.setRepPassword("");
        boolean b = sysUserFacade.updatePwd(vo, newPwd);
        if (b) {
            return success("密码修改成功");
        }
        return error("密码修改失败");
    }

    @ApiOperation(value = "重置密码", notes = "")
    @PostMapping(value = "/resetPwd")
    public Object resetPwd(@Validated @RequestBody SysUserResetPwdReqVo vo) {
        if (!StringUtils.equals(vo.getNewPassword(), vo.getRepPassword())) {
            throw new IatpException("两次输入密码不一致");
        }
        vo.setNewPassword(new SCryptPasswordEncoder().encode(vo.getNewPassword()));
        vo.setRepPassword("");
        boolean b = sysUserFacade.resetPwd(vo);
        if (b) {
            return success("密码重置成功");
        }
        return error("密码重置失败");
    }

    @ApiOperation(value = "用户详情", notes = "")
    @GetMapping(value = "/details/{id}")
    public Object details(@PathVariable("id") @NotNull(message = "字典类型ID不能为空") Long id) {
        SysUser sysUser = sysUserFacade.details(id);
        sysUser.setPassword("");
        return success(sysUser);
    }

    @ApiOperation(value = "用户信息", notes = "")
    @GetMapping(value = "/userInfo")
    public Object userInfo() {
        SysUser sysUser = sysUserFacade.details(SecurityUtils.getCurrentUserId());
        sysUser.setPassword("");
        return success(sysUser);
    }

    @ApiOperation(value = "用户授权", notes = "")
    @PostMapping("/auth")
    public Object add(@Validated @RequestBody SysUserAuthVo authVo) {
        boolean b = sysUserFacade.auth(authVo);
        if (b) {
            return success("用户授权成功");
        }
        return error("用户授权失败");
    }

    @PostMapping("/update/account")
    @ApiOperation(value = "修改帐号信息",notes ="")
    public Object updateUserInfo(@Validated @RequestBody SysUserUpdateReqVo editVo){
        boolean b = sysUserFacade.accountUpdate(editVo);
        if(b){
            return success("修改用户成功");
        }
        return error("修改用户失败");
    }

    @ApiOperation(value = "批量删除用户", notes = "")
    @PostMapping(value = "/batchDel")
    public Object batchDel(String ids) {
        List<String> strings = Splitter.on(",").splitToList(ids);
        boolean b = sysUserFacade.batchDel(strings);
        if (b) {
            return success("批量删除用户成功");
        }
        return error("批量删除用户失败");
    }
}
