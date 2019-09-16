package cn.powertime.iatp.admin.controller;

import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.entity.SysRole;
import cn.powertime.iatp.enums.HttpStatus;
import cn.powertime.iatp.facade.admin.SysRoleFacade;
import cn.powertime.iatp.vo.req.admin.*;
import cn.powertime.iatp.vo.resp.admin.SysRolePageListVo;
import cn.powertime.iatp.vo.resp.admin.UserRoleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.base.Splitter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : liqi
 * Date: 2019-04-17
 * Time: 16:55
 */
@RestController
@RequestMapping(value = "/role")
@Api(value = "/role", tags = "角色接口", produces = MediaType.ALL_VALUE)
public class RoleController extends BaseController {

    @Autowired
    private SysRoleFacade sysRoleFacade;

    @ApiOperation(value = "验证角色名称及编码唯一性", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型,1:查询名称是否重复，2:查询编码是否重复", required = true),
            @ApiImplicitParam(name = "value", value = "具体值", required = true)})
    @PostMapping("/checkNameAndCodeOnly")
    public Object checkNameAndCodeOnly(@RequestParam int type, @RequestParam String value, Long id) {
        boolean b = sysRoleFacade.checkNameAndCodeOnly(type, value, id);
        if (b) {
            if (type == 1) {
                return error(HttpStatus.CHECK_FAIL, null, "名称已经存在");
            } else {
                return error(HttpStatus.CHECK_FAIL, null, "编码已经存在");
            }
        }
        if (type == 1) {
            return success("名称可用");
        } else {
            return success("编码可用");
        }
    }

    @ApiOperation(value = "新增角色", notes = "")
    @PostMapping("/add")
    public Object add(@Validated @RequestBody SysRoleAddVo addVo) {
        boolean b = sysRoleFacade.add(addVo);
        if (b) {
            return success("新增角色成功");
        }
        return error("新增角色失败");
    }

    @ApiOperation(value = "修改角色", notes = "")
    @PostMapping("/edit")
    public Object edit(@Validated @RequestBody SysRoleEditVo editVo) {
        boolean b = sysRoleFacade.edit(editVo);
        if (b) {
            return success("修改角色成功");
        }
        return error("修改角色失败");
    }

    @ApiOperation(value = "删除角色", notes = "")
    @PostMapping("/del")
    public Object del(Long id) {
        boolean b = sysRoleFacade.del(id);
        if (b) {
            return success("删除角色成功");
        }
        return error("删除角色失败");
    }

    @ApiOperation(value = "角色分页列表", notes = "")
    @PostMapping(value = "/list")
    public Object list(@Validated @RequestBody ParamPageVo<SysRoleListVo> vo) {
        IPage<SysRolePageListVo> list = sysRoleFacade.list(vo);
        return success(list);
    }

    @ApiOperation(value = "角色列表", notes = "")
    @PostMapping(value = "/listAll")
    public Object list() {
        List<SysRole> list = sysRoleFacade.listAll();
        return success(list);
    }

    @ApiOperation(value = "用户角色关系列表", notes = "")
    @GetMapping(value = "/listAll/{uid}")
    public Object userRole(@PathVariable("uid") @NotNull(message = "用户ID不能为空") Long uid) {
        List<UserRoleVo> list = sysRoleFacade.userRole(uid);
        return success(list);
    }


    @ApiOperation(value = "角色详情", notes = "")
    @GetMapping(value = "/details/{id}")
    public Object details(@PathVariable("id") @NotNull(message = "ID不能为空") Long id) {
        return success(sysRoleFacade.selectById(id));
    }

    @ApiOperation(value = "角色授权", notes = "")
    @PostMapping("/auth")
    public Object add(@Validated @RequestBody SysRoleAuthVo authVo) {
        boolean b = sysRoleFacade.auth(authVo);
        if (b) {
            return success("角色授权成功");
        }
        return error("角色授权失败");
    }

    @ApiOperation(value = "批量删除角色", notes = "")
    @PostMapping(value = "/batchDel")
    public Object batchDel(String ids) {
        List<String> strings = Splitter.on(",").splitToList(ids);
        boolean b = sysRoleFacade.batchDel(strings);
        if (b) {
            return success("批量删除角色成功");
        }
        return error("批量删除角色失败");
    }

}
