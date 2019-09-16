package cn.powertime.iatp.admin.controller;

import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.enums.HttpStatus;
import cn.powertime.iatp.facade.admin.SysDictFacade;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.SysDictAddVo;
import cn.powertime.iatp.vo.req.admin.SysDictEditVo;
import cn.powertime.iatp.vo.req.admin.SysDictListVo;
import cn.powertime.iatp.vo.resp.admin.SysDictVo;
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
 * @author ZYW
 * @version 1.0
 * @date 2019-04-16 19:01
 */
@RestController
@RequestMapping(value = "/dict")
@Api(value = "/dict", tags = "字典接口", produces = MediaType.ALL_VALUE)
public class DictController extends BaseController {

    @Autowired
    private SysDictFacade sysDictFacade;

    @ApiOperation(value = "验证字典名称及值唯一性", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型,1:查询名称是否重复，2:查询值是否重复", required = true),
            @ApiImplicitParam(name = "value", value = "具体值", required = true),
            @ApiImplicitParam(name = "typeId", value = "字典类型ID", required = true)})
    @PostMapping("/checkNameAndValueOnly")
    public Object checkNameAndCodeOnly(@RequestParam int type, @RequestParam String value, Long id, @RequestParam Long typeId) {
        boolean b = sysDictFacade.checkNameAndValueOnly(type, value, id, typeId);
        if (b) {
            if (type == 1) {
                return error(HttpStatus.CHECK_FAIL, null, "字典名称已经存在");
            } else {
                return error(HttpStatus.CHECK_FAIL, null, "字典值已经存在");
            }
        }
        if (type == 1) {
            return success("字典名称可用");
        } else {
            return success("字典值可用");
        }
    }

    @ApiOperation(value = "根据字典类型查询字典", notes = "")
    @GetMapping(value = "/list/{typeId}")
    public Object list(@PathVariable("typeId") @NotNull(message = "字典类型ID不能为空") Long typeId) {
        return success(sysDictFacade.selectByTypeId(typeId));
    }

    @ApiOperation(value = "新增字典", notes = "")
    @PostMapping(value = "/add")
    public Object add(@Validated @RequestBody SysDictAddVo vo) {
        boolean insert = sysDictFacade.add(vo);
        if (insert) {
            return success("新增字典成功");
        }
        return error("新增字典失败");
    }

    @ApiOperation(value = "修改字典", notes = "")
    @PostMapping(value = "/edit")
    public Object edit(@Validated @RequestBody SysDictEditVo vo) {
        boolean b = sysDictFacade.edit(vo);
        if (b) {
            return success("修改字典成功");
        }
        return error("修改字典失败");
    }

    @ApiOperation(value = "删除字典", notes = "")
    @PostMapping(value = "/del")
    public Object del(Long id) {
        boolean b = sysDictFacade.del(id);
        if (b) {
            return success("删除典成功");
        }
        return error("删除字典失败");
    }

    @ApiOperation(value = "字典类型分页列表", notes = "")
    @PostMapping(value = "/list")
    public Object list(@Validated @RequestBody ParamPageVo<SysDictListVo> vo) {
        Page<SysDictVo> list = sysDictFacade.list(vo);
        return success(list);
    }

    @ApiOperation(value = "字典详情", notes = "")
    @GetMapping(value = "/details/{id}")
    public Object details(@PathVariable("id") @NotNull(message = "字典类型ID不能为空") Long id) {
        return success(sysDictFacade.selectById(id));
    }

    @ApiOperation(value = "批量删除字典", notes = "")
    @PostMapping(value = "/batchDel")
    public Object batchDel(String ids) {
        List<String> strings = Splitter.on(",").splitToList(ids);
        boolean b = sysDictFacade.batchDel(strings);
        if (b) {
            return success("批量删除字典成功");
        }
        return error("批量删除字典失败");
    }

}
