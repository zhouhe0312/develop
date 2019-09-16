package cn.powertime.iatp.admin.controller;

import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.facade.admin.SysSafeStrategyFacade;
import cn.powertime.iatp.vo.req.admin.SysSafeStrategyEditVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : liqi
 * Date: 2019-04-17
 * Time: 16:55
 */
@RestController
@RequestMapping(value = "/sysSafeStrategy")
@Api(value = "/sysSafeStrategy", tags = "安全策略接口", produces = MediaType.ALL_VALUE)
public class SysSafeStrategyController extends BaseController {

    @Autowired
    private SysSafeStrategyFacade sysSafeStrategyFacade;

    @ApiOperation(value = "修改安全策略", notes = "")
    @PostMapping("/edit")
    public Object edit(@Validated @RequestBody SysSafeStrategyEditVo editVo) {
        boolean b = sysSafeStrategyFacade.edit(editVo);
        if (b) {
            return success("修改安全策略成功");
        }
        return error("修改安全策略失败");
    }

    @ApiOperation(value = "安全策略详情", notes = "")
    @GetMapping(value = "/details")
    public Object details() {
        return success(sysSafeStrategyFacade.getOne());
    }

    @ApiOperation(value = "返回密码强度")
    @GetMapping(value = "/passwordStrength")
    public Object passwordStrength(@RequestParam String password) {
        int strength = sysSafeStrategyFacade.passwordStrength(password);
        return success(strength);
    }

}
