package cn.powertime.iatp.admin.controller;

import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.enums.HttpStatus;
import cn.powertime.iatp.facade.admin.SysDeptFacade;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.SysDeptAddVo;
import cn.powertime.iatp.vo.req.admin.SysDeptEditVo;
import cn.powertime.iatp.vo.req.admin.SysDeptListVo;
import cn.powertime.iatp.vo.resp.admin.DeptTree;
import cn.powertime.iatp.vo.resp.admin.SysDeptPageListVo;
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
@RequestMapping(value = "/dept")
@Api(value = "/dept", tags = "部门接口", produces = MediaType.ALL_VALUE)
public class SysDeptController extends BaseController {

    @Autowired
    private SysDeptFacade sysDeptFacade;

    @ApiOperation(value = "验证名称或编码唯一性")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型,1:查询名称是否重复，2:查询编码是否重复", required = true),
            @ApiImplicitParam(name = "value", value = "具体值", required = true)})
    @GetMapping("/checkNameAndCodeOnly")
    public Object checkNameAndCodeOnly(@RequestParam int type, @RequestParam String value, Long id) {
        boolean b = sysDeptFacade.checkNameAndCodeOnly(type, value, id);
        if (b) {
            if (type == 1) {
                return error(HttpStatus.CHECK_FAIL, null, "部门名称已经存在");
            } else {
                return error(HttpStatus.CHECK_FAIL, null, "部门编码已经存在");
            }
        }
        if (type == 1) {
            return success("部门名称可用");
        } else {
            return success("部门编码可用");
        }
    }

    @ApiOperation(value = "新增部门", notes = "")
    @PostMapping("/add")
    public Object add(@Validated @RequestBody SysDeptAddVo addVo) {
        boolean b = sysDeptFacade.add(addVo);
        if (b) {
            return success("新增部门成功");
        }
        return error("新增部门失败");
    }

    @ApiOperation(value = "修改部门", notes = "")
    @PostMapping("/edit")
    public Object edit(@Validated @RequestBody SysDeptEditVo editVo) {
        boolean b = sysDeptFacade.edit(editVo);
        if (b) {
            return success("修改部门成功");
        }
        return error("修改部门失败");
    }

    @ApiOperation(value = "删除部门", notes = "")
    @PostMapping("/del")
    public Object del(Long id) {
        boolean b = sysDeptFacade.del(id);
        if (b) {
            return success("删除部门成功");
        }
        return error("删除部门失败");
    }

    @ApiOperation(value = "部门分页列表", notes = "")
    @PostMapping(value = "/list")
    public Object list(@Validated @RequestBody ParamPageVo<SysDeptListVo> vo) {
        IPage<SysDeptPageListVo> list = sysDeptFacade.list(vo);
        return success(list);
    }

    @ApiOperation(value = "部门详情", notes = "")
    @GetMapping(value = "/details/{id}")
    public Object details(@PathVariable("id") @NotNull(message = "ID不能为空") Long id) {
        return success(sysDeptFacade.selectById(id));
    }

    @ApiOperation(value = "批量删除部门", notes = "")
    @PostMapping(value = "/batchDel")
    public Object batchDel(String ids) {
        List<String> strings = Splitter.on(",").splitToList(ids);
        boolean b = sysDeptFacade.batchDel(strings);
        if (b) {
            return success("批量删除部门" +
                    "成功");
        }
        return error("批量删除角色失败");
    }

    @ApiOperation(value = "部门tree")
    @ApiImplicitParam(name = "type", value = "类型,0:查询所有部门(包含禁用的)，1:查询启用的部门", required = true)
    @PostMapping("/tree")
    public Object tree(@RequestParam(defaultValue = "1", value = "type") int type) {
        List<DeptTree> tree = sysDeptFacade.tree(type);
        return success(tree);
    }

}
