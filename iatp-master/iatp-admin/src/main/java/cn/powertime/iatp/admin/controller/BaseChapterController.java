package cn.powertime.iatp.admin.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.enums.HttpStatus;
import cn.powertime.iatp.facade.admin.BaseChapterFacade;
import cn.powertime.iatp.vo.req.admin.BaseChapterAddVo;
import cn.powertime.iatp.vo.req.admin.BaseChapterEditVo;
import cn.powertime.iatp.vo.resp.admin.CommonTree;
import cn.powertime.iatp.vo.resp.admin.KeyValueVo;
import com.google.common.base.Splitter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 课程（实验）章节表 前端控制器
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Api(value = "/base-chapter", tags = {"课程（实验）章节表 前端控制器"})
@RestController
@RequestMapping("/base-chapter")
public class BaseChapterController extends BaseController {

    @Autowired
    private BaseChapterFacade baseChapterFacade;

    @ApiOperation(value = "验证名称唯一性")
    @GetMapping("/checkNameOnly")
    public Object checkNameOnly(@RequestParam Long courseId, @RequestParam Long pid, @RequestParam int type, @RequestParam String name) {
        boolean b = baseChapterFacade.checkNameOnly(courseId, pid, type, name, null);
        if (b) {
            return error(HttpStatus.CHECK_FAIL, null, "名称已经存在");
        }
        return success("名称可用");
    }

    @ApiOperation(value = "新增课程（实验）章节表")
    @PostMapping(value = "/add")
    public Object add(@Validated @RequestBody BaseChapterAddVo vo) {
        boolean insert = baseChapterFacade.add(vo);
        if (insert) {
            return success("新增章节成功");
        }
        return error("新增章节失败");
    }

    @ApiOperation(value = "修改课程（实验）章节表")
    @PostMapping(value = "/edit")
    public Object edit(@Validated @RequestBody BaseChapterEditVo vo) {
        boolean b = baseChapterFacade.edit(vo);
        if (b) {
            return success("修改章节成功");
        }
        return error("修改章节失败");
    }

    @ApiOperation(value = "课程管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型,1:传courseId，2:传pid", required = true),
            @ApiImplicitParam(name = "pidOrCourseId", value = "具体值", required = true)})
    @PostMapping(value = "/select/list")
    public Object listAll(@RequestParam(defaultValue = "1") Integer type, @RequestParam(defaultValue = "0") Long pidOrCourseId) {
        List<KeyValueVo> list = baseChapterFacade.selectList(type, pidOrCourseId);
        return success(list);
    }

    @ApiOperation(value = "批量删除课程（实验）章节表")
    @PostMapping(value = "/batchDel/{ids}")
    public Object batchDel(@PathVariable("ids") @NotNull(message = "ids不能为空") String ids) {
        List<String> strings = Splitter.on(",").splitToList(ids);
        boolean b = baseChapterFacade.batchDel(strings);
        if (b) {
            return success("删除成功");
        }
        return error("删除失败");
    }

    @ApiOperation(value = "课程（实验）章节tree")
    @PostMapping("/tree")
    public Object tree(@RequestParam(defaultValue = "0", value = "type") Integer type, @RequestParam(defaultValue = "0", value = "courseId") Long courseId) {
        List<CommonTree> tree = baseChapterFacade.tree(type, courseId);
        return success(tree);
    }

}
