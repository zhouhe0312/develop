package cn.powertime.iatp.admin.controller;

import cn.powertime.iatp.authres.security.SecurityUtils;
import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.enums.HttpStatus;
import cn.powertime.iatp.facade.admin.SysResFacade;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.SysMenuListVo;
import cn.powertime.iatp.vo.req.admin.SysResAddVo;
import cn.powertime.iatp.vo.req.admin.SysResEditVo;
import cn.powertime.iatp.vo.resp.admin.ElementTreeVo;
import cn.powertime.iatp.vo.resp.admin.MenuTree;
import cn.powertime.iatp.vo.resp.admin.SysResPageVo;
import cn.powertime.iatp.vo.resp.admin.ZtreeVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * Date: 2019-04-18
 * Time: 9:55
 */
@RestController
@RequestMapping(value = "/res")
@Api(value = "/res", tags = "权限接口", produces = MediaType.ALL_VALUE)
public class ResController extends BaseController {

    @Autowired
    private SysResFacade sysResFacade;

    @ApiOperation(value = "验证权限名称及编码唯一性", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型,1:查询名称是否重复，2:查询编码是否重复", required = true),
            @ApiImplicitParam(name = "value", value = "具体值", required = true)})
    @PostMapping("/checkNameAndCodeOnly")
    public Object checkNameAndCodeOnly(@RequestParam int type, @RequestParam String value, Long id) {
        boolean b = sysResFacade.checkNameAndCodeOnly(type, value, id);
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

    @ApiOperation(value = "新增权限", notes = "")
    @PostMapping("/add")
    public Object add(@Validated @RequestBody SysResAddVo addVo) {
        boolean b = sysResFacade.add(addVo);
        if (b) {
            return success("新增权限成功");
        }
        return error("新增权限失败");
    }

    @ApiOperation(value = "修改权限", notes = "")
    @PostMapping("/edit")
    public Object edit(@Validated @RequestBody SysResEditVo editVo) {
        boolean b = sysResFacade.edit(editVo);
        if (b) {
            return success("修改权限成功");
        }
        return error("修改权限失败");
    }

    @ApiOperation(value = "删除权限", notes = "")
    @PostMapping("/del")
    public Object del(Long id) {
        boolean b = sysResFacade.del(id);
        if (b) {
            return success("删除权限成功");
        }
        return error("删除权限失败");
    }

    @ApiOperation(value = "权限分页列表", notes = "")
    @PostMapping(value = "/list")
    public Object list(@Validated @RequestBody ParamPageVo<SysMenuListVo> vo) {
        Page<SysResPageVo> list = sysResFacade.list(vo);
        return success(list);
    }

    @ApiOperation(value = "角色授权ztree (预留接口)")
    @PostMapping("/ztree")
    public Object ztree(Long roleId) {
        List<ZtreeVo> tree = sysResFacade.ztree(roleId);
        return success(tree);
    }

    @ApiOperation(value = "角色授权elementTree")
    @PostMapping("/elementTree")
    public Object elementTree(Long roleId) {
        ElementTreeVo tree = sysResFacade.elementTree(roleId);
        return success(tree);
    }

    @ApiOperation(value = "权限tree")
    @PostMapping("/tree")
    public Object tree() {
        List<MenuTree> tree = sysResFacade.tree();
        return success(tree);
    }

    @ApiOperation(value = "权限详情", notes = "")
    @GetMapping(value = "/details/{id}")
    public Object details(@PathVariable("id") @NotNull(message = "权限ID不能为空") Long id) {
        return success(sysResFacade.selectById(id));
    }

    @ApiOperation(value = "获取用户权限下的资源（权限）", notes = "")
    @GetMapping(value = "/menuTree")
    public Object menuTree() {
        return success(sysResFacade.menuTreeByUserId(SecurityUtils.getCurrentUserId()));
    }

    @ApiOperation(value = "批量删除权限", notes = "")
    @PostMapping(value = "/batchDel")
    public Object batchDel(String ids) {
        List<String> strings = Splitter.on(",").splitToList(ids);
        boolean b = sysResFacade.batchDel(strings);
        if (b) {
            return success("批量删除权限成功");
        }
        return error("批量删除权限失败");
    }

}
