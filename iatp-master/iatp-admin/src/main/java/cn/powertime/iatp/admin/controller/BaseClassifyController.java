package cn.powertime.iatp.admin.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.entity.BaseClassify;
import cn.powertime.iatp.enums.HttpStatus;
import cn.powertime.iatp.facade.admin.BaseClassifyFacade;
import cn.powertime.iatp.vo.req.admin.BaseClassifyAddVo;
import cn.powertime.iatp.vo.req.admin.BaseClassifyEditVo;
import cn.powertime.iatp.vo.req.admin.BaseClassifySearchVo;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.resp.admin.BaseClassifyPageListVo;
import cn.powertime.iatp.vo.resp.admin.CommonTree;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Splitter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Api(value = "/base-classify", tags = {"分类表 前端控制器"})
@RestController
@RequestMapping("/base-classify")
public class BaseClassifyController extends BaseController {
    @Autowired
    private BaseClassifyFacade baseClassifyFacade;

    @ApiOperation(value = "验证名称唯一性")
    @GetMapping("/checkNameOnly")
    public Object checkNameOnly(@RequestParam Long pid, @RequestParam int type, @RequestParam String name) {
        boolean b = baseClassifyFacade.checkNameOnly(pid, type, name, null);
        if (b) {
            return error(HttpStatus.CHECK_FAIL, null, "名称已经存在");
        }
        return success("名称可用");
    }

    @ApiOperation(value = "新增分类", notes = "")
    @PostMapping(value = "/add")
    public Object add(@Validated @RequestBody BaseClassifyAddVo vo) {
        boolean insert = baseClassifyFacade.add(vo);
        if (insert) {
            return success("新增分类成功");
        }
        return error("新增分类失败");
    }

    @ApiOperation(value = "修改分类", notes = "")
    @PostMapping(value = "/edit")
    public Object edit(@Validated @RequestBody BaseClassifyEditVo vo) {
        boolean b = baseClassifyFacade.edit(vo);
        if (b) {
            return success("修改分类成功");
        }
        return error("修改分类失败");
    }

    @ApiOperation(value = "分类管理分页列表", notes = "")
    @PostMapping(value = "/list")
    public Object list(@Validated @RequestBody ParamPageVo<BaseClassifySearchVo> vo) {
        Page<BaseClassifyPageListVo> list = baseClassifyFacade.list(vo);
        return success(list);
    }

    @ApiOperation(value = "分类管理列表", notes = "")
    @PostMapping(value = "/listAll")
    public Object listAll(int type) {
        List<BaseClassify> list = baseClassifyFacade.listAll(type);
        return success(list);
    }

    @ApiOperation(value = "批量删除分类", notes = "")
    @PostMapping(value = "/batchDel/{ids}")
    public Object batchDel(@PathVariable("ids") @NotNull(message = "ids不能为空") String ids) {
        List<String> strings = Splitter.on(",").splitToList(ids);
        boolean b = baseClassifyFacade.batchDel(strings);
        if (b) {
            return success("删除成功");
        }
        return error("删除失败");
    }

    @ApiOperation(value = "启用禁用分类", notes = "")
    @PostMapping(value = "/enableDisabled/{id}")
    public Object enableDisabled(@PathVariable("id") @NotNull(message = "分类ID不能为空") Long id) {
        boolean b = baseClassifyFacade.enableDisabled(id);
        if (b) {
            return success("操作成功");
        }
        return error("操作失败");
    }

    @ApiOperation(value = "分类tree")
    @PostMapping("/tree")
    public Object tree() {
        List<CommonTree> tree = baseClassifyFacade.tree();
        return success(tree);
    }

}
