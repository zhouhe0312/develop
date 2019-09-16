package cn.powertime.iatp.admin.controller;

import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.entity.SysDictType;
import cn.powertime.iatp.facade.admin.SysDictTypeFacade;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.SysDictTypeListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


/**
 * @author liqi
 */
@RestController
@RequestMapping(value = "/dict/type")
@Api(value = "/dict/type",tags = "字典类型接口",produces = MediaType.ALL_VALUE)
public class DictTypeController extends BaseController {

    @Autowired
    private SysDictTypeFacade sysDictTypeFacede;

    @ApiOperation(value = "验证名称唯一性", notes = "")
    @PostMapping("/checkNameOnly")
    public Object checkNameOnly(@RequestParam String name) {
        boolean b = sysDictTypeFacede.checkNameOnly(name, null);
        if (b) {
            return error("名称已经存在");
        }
        return success("名称可用");
    }

    @ApiOperation(value = "字典类型新增",notes ="")
    @PostMapping(value = "/add")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",dataType = "String",paramType="query")
    })
    public Object add(String name){
        boolean insert = sysDictTypeFacede.add(name);
        if (insert){
            return success("新增字典类型成功");
        }
        return error("新增字典类型失败");
    }

    @ApiOperation(value = "字典类型修改",notes ="")
    @PostMapping(value = "/edit")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",dataType = "Long",paramType="query"),
            @ApiImplicitParam(name="name",dataType = "String",paramType="query")
    })
    public Object edit(String name, Long id){
        boolean update = sysDictTypeFacede.edit(name,id);
        if (update){
            return success("修改字典类型成功");
        }
        return error("修改字典类型失败");
    }

    @ApiOperation(value = "删除字典类型",notes ="")
    @GetMapping(value = "/del/{id}")
    public Object del(@PathVariable("id") @NotNull(message = "字典类型ID不能为空") Long id){
        boolean del = sysDictTypeFacede.del(id);
        if (del){
            return success("删除字典类型成功");
        }
        return error("删除字典类型失败");
    }

    @ApiOperation(value = "全量字典类型",notes ="")
    @GetMapping(value = "/listAll")
    public Object listAll(){
        return success(sysDictTypeFacede.selectList());
    }

    @ApiOperation(value = "字典类型分页列表",notes ="")
    @PostMapping(value = "/list")
    public Object list(@Validated @RequestBody ParamPageVo<SysDictTypeListVo> vo) {
        Page<SysDictType> list = sysDictTypeFacede.list(vo);
        return success(list);
    }

    @ApiOperation(value = "字典类型详情",notes ="")
    @GetMapping(value = "/details/{id}")
    public Object details(@PathVariable("id") @NotNull(message = "字典类型ID不能为空") Long id){
        return success(sysDictTypeFacede.selectById(id));
    }


}
