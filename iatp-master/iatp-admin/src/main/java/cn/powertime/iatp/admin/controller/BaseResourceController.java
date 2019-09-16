package cn.powertime.iatp.admin.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.entity.BaseResource;
import cn.powertime.iatp.enums.HttpStatus;
import cn.powertime.iatp.facade.admin.BaseResourceFacade;
import cn.powertime.iatp.vo.req.admin.*;
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
 * 课程（实验）资源表 前端控制器
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Api(value = "/base-resource", tags = {"课程（实验）资源表 前端控制器"})
@RestController
@RequestMapping("/base-resource")
public class BaseResourceController extends BaseController {

    @Autowired
    private BaseResourceFacade baseResourceFacade;

    @ApiOperation(value = "验证名称唯一性")
    @GetMapping("/checkNameOnly")
    public Object checkNameOnly(@RequestParam Long chapterId, @RequestParam int type, @RequestParam String name) {
        boolean b = baseResourceFacade.checkNameOnly(chapterId, type, name, null);
        if (b) {
            return error(HttpStatus.CHECK_FAIL, null, "名称已经存在");
        }
        return success("名称可用");
    }

    @ApiOperation(value = "新增资源", notes = "")
    @PostMapping(value = "/addResource")
    public Object add(@Validated @RequestBody BaseResourceAddVo vo) {
        boolean insert = baseResourceFacade.add(vo);
        if (insert) {
            return success("新增资源成功");
        }
        return error("新增资源失败");
    }

    @ApiOperation(value = "新增实训", notes = "")
    @PostMapping(value = "/addResourceTraining")
    public Object addTraining(@Validated @RequestBody BaseResourceTrainingAddVo vo) {
        boolean insert = baseResourceFacade.addTraining(vo);
        if (insert) {
            return success("新增实训成功");
        }
        return error("新增实训失败");
    }

    @ApiOperation(value = "修改资源", notes = "")
    @PostMapping(value = "/editResource")
    public Object edit(@Validated @RequestBody BaseResourceEditVo vo) {
        boolean b = baseResourceFacade.edit(vo);
        if (b) {
            return success("修改资源成功");
        }
        return error("修改资源失败");
    }

    @ApiOperation(value = "修改实训", notes = "")
    @PostMapping(value = "/editResourceTraining")
    public Object editTraining(@Validated @RequestBody BaseResourceTrainingEditVo vo) {
        boolean b = baseResourceFacade.editTraining(vo);
        if (b) {
            return success("修改实训成功");
        }
        return error("修改实训失败");
    }

    @ApiOperation(value = "资源列表", notes = "")
    @PostMapping(value = "/listAll")
    public Object listAll(Long chapterPid, Long chapterId) {
        List<BaseResource> list = baseResourceFacade.listAll(chapterPid, chapterId);
        return success(list);
    }

    @ApiOperation(value = "批量删除课程（实验）章节表", notes = "")
    @PostMapping(value = "/batchDel/{ids}")
    public Object batchDel(@PathVariable("ids") @NotNull(message = "ids不能为空") String ids) {
        List<String> strings = Splitter.on(",").splitToList(ids);
        boolean b = baseResourceFacade.batchDel(strings);
        if (b) {
            return success("删除成功");
        }
        return error("删除失败");
    }

    @ApiOperation(value = "拖动排序", notes = "")
    @PostMapping(value = "/sort")
    public Object sort(@Validated @RequestBody BaseResourceSortVo vo) {
        boolean b = baseResourceFacade.sort(vo);
        if (b) {
            return success("排序成功");
        }
        return error("排序失败");
    }

}
