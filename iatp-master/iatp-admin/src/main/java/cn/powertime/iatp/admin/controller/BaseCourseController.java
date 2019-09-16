package cn.powertime.iatp.admin.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.entity.BaseCourse;
import cn.powertime.iatp.enums.HttpStatus;
import cn.powertime.iatp.exception.FileServerException;
import cn.powertime.iatp.facade.admin.BaseCourseFacade;
import cn.powertime.iatp.vo.req.admin.*;
import cn.powertime.iatp.vo.resp.admin.KeyValueVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Splitter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 课程（实验）表 前端控制器
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Api(value = "/base-course", tags = {"课程（实验）表 前端控制器"})
@RestController
@RequestMapping("/base-course")
public class BaseCourseController extends BaseController {

    @Autowired
    private BaseCourseFacade baseCourseFacade;

    @ApiOperation(value = "验证名称唯一性")
    @GetMapping("/checkNameOnly")
    public Object checkNameOnly(@RequestParam int type, @RequestParam String name) {
        boolean b = baseCourseFacade.checkNameOnly(type, name, null);
        if (b) {
            return error(HttpStatus.CHECK_FAIL, null, "名称已经存在");
        }
        return success("名称可用");
    }

    @ApiOperation(value = "下拉框中使用的课程实验列表")
    @GetMapping("/select/list")
    public Object selectList(@RequestParam(defaultValue = "0", value = "type") Integer type) {
        List<KeyValueVo> keyValueVoList = baseCourseFacade.selectList(type);
        return success(keyValueVoList);
    }

    @ApiOperation(value = "创建课程/实验")
    @PostMapping(value = "/add")
    public Object add(@Validated @RequestBody BaseCourseAddVo vo) {
        boolean insert = baseCourseFacade.add(vo);
        if (insert) {
            return success("创建成功");
        }
        return error("创建失败");
    }

    @ApiOperation(value = "修改课程/实验")
    @PostMapping(value = "/edit")
    public Object edit(@Validated @RequestBody BaseCourseEditVo vo) {
        boolean b = baseCourseFacade.edit(vo);
        if (b) {
            return success("修改成功");
        }
        return error("修改失败");
    }

    @ApiOperation(value = "课程管理分页列表")
    @PostMapping(value = "/list")
    public Object list(@Validated @RequestBody ParamPageVo<BaseCourseSearchVo> vo) {
        Page<BaseCourse> list = baseCourseFacade.list(vo);
        return success(list);
    }

    @ApiOperation(value = "批量删除课程/实验")
    @PostMapping(value = "/batchDel/{ids}")
    public Object batchDel(@PathVariable("ids") @NotNull(message = "ids不能为空") String ids) {
        List<String> strings = Splitter.on(",").splitToList(ids);
        boolean b = baseCourseFacade.batchDel(strings);
        if (b) {
            return success("删除成功");
        }
        return error("删除失败");
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入课程/实验")
    public Object upload(MultipartFile file, BaseCourseUploadVo vo) {
        if (file == null || file.isEmpty()) {
            throw new FileServerException("上传文件File为空！");
        }
        boolean b = baseCourseFacade.importExcel(file, vo);
        if (b) {
            return success("导入成功");
        } else {
            return error("导入失败");
        }
    }

}
