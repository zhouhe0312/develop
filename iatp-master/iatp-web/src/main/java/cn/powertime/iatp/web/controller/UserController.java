package cn.powertime.iatp.web.controller;


import cn.powertime.iatp.authres.security.SecurityUtils;
import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.facade.web.UserFacade;
import cn.powertime.iatp.vo.req.web.UserRegisterVo;
import cn.powertime.iatp.vo.req.web.UserUpdatePwdVo;
import cn.powertime.iatp.vo.req.web.UserUpdateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author yang xin
 * @date 2019-06-3
 */
@RestController
@RequestMapping("/user")
@Api(value = "/user", tags = "用户接口", produces = MediaType.ALL_VALUE)
public class UserController extends BaseController {

    @Autowired
    private UserFacade userFacade;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public Object register(@Validated @RequestBody UserRegisterVo registerVo) {
        boolean b = userFacade.register(registerVo);
        if (b) {
            return success("用户注册成功");
        }
        return error("用户注册失败");
    }

    @ApiOperation(value = "用户详情")
    @GetMapping(value = "/details")
    public Object details() {
        return success(userFacade.details(SecurityUtils.getCurrentUserId()));
    }

    @ApiOperation(value = "账号设置")
    @PostMapping("/updatePwd")
    public Object updatePwd(@Validated @RequestBody UserUpdatePwdVo vo) {
        boolean b = userFacade.updatePwd(vo);
        if (b) {
            return success("设置成功");
        }
        return error("设置失败");
    }

    @ApiOperation(value = "个人资料修改")
    @PostMapping("/updateUser")
    public Object updateUser(@Validated @RequestBody UserUpdateVo vo) {
        boolean b = userFacade.updateUser(vo);
        if (b) {
            return success("修改成功");
        }
        return error("修改失败");
    }

    @GetMapping("/passwordCheck")
    @ApiOperation(value = "密码校验")
    public Object passwordCheck(@RequestParam String password) {
        boolean b = userFacade.passwordCheck(password);
        if (b) {
            return success("密码校验成功");
        }
        return error("密码校验失败");
    }

    @GetMapping("/passwordTips")
    @ApiOperation(value = "密码提示语接口", notes = "返回当前系统设置的密码强度提示语")
    public Object passwordTips() {
        return success(userFacade.passwordTips());
    }

}
