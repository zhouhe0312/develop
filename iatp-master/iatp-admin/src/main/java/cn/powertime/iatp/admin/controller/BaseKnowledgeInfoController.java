package cn.powertime.iatp.admin.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.entity.BaseKnowledgeInfo;
import cn.powertime.iatp.enums.HttpStatus;
import cn.powertime.iatp.facade.admin.BaseKnowledgeInfoFacade;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoAddVo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoEditVo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoSearchVo;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
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
 * 知识表 前端控制器
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Api(value = "/base-knowledge-info", tags = {"知识库接口"})
@RestController
@RequestMapping("/base-knowledge-info")
public class BaseKnowledgeInfoController extends BaseController {

    @Autowired
    private BaseKnowledgeInfoFacade baseKnowledgeInfoFacade;

    @ApiOperation(value = "验证名称唯一性")
    @GetMapping("/checkNameOnly")
    public Object checkNameOnly(@RequestParam String name) {
        boolean b = baseKnowledgeInfoFacade.checkNameOnly(name, null);
        if (b) {
            return error(HttpStatus.CHECK_FAIL, null, "名称已经存在");
        }
        return success("名称可用");
    }

    @ApiOperation(value = "新增知识库")
    @PostMapping(value = "/add")
    public Object add(@Validated @RequestBody BaseKnowledgeInfoAddVo vo) {
        boolean insert = baseKnowledgeInfoFacade.add(vo);
        if (insert) {
            return success("新增知识库成功");
        }
        return error("新增知识库失败");
    }

    @ApiOperation(value = "修改知识库")
    @PostMapping(value = "/edit")
    public Object edit(@Validated @RequestBody BaseKnowledgeInfoEditVo vo) {
        boolean b = baseKnowledgeInfoFacade.edit(vo);
        if (b) {
            return success("修改知识库成功");
        }
        return error("修改知识库失败");
    }

    @ApiOperation(value = "知识库分页列表")
    @PostMapping(value = "/list")
    public Object list(@Validated @RequestBody ParamPageVo<BaseKnowledgeInfoSearchVo> vo) {
        Page<BaseKnowledgeInfo> list = baseKnowledgeInfoFacade.list(vo);
        return success(list);
    }

    @ApiOperation(value = "知识库列表")
    @PostMapping(value = "/listAll")
    public Object listAll() {
        List<BaseKnowledgeInfo> list = baseKnowledgeInfoFacade.listAll();
        return success(list);
    }

    @ApiOperation(value = "详情")
    @GetMapping(value = "/details/{id}")
    public Object details(@PathVariable("id") @NotNull(message = "id不能为空") Long id) {
        return success(baseKnowledgeInfoFacade.selectById(id));
    }

    @ApiOperation(value = "批量删除知识库")
    @PostMapping(value = "/batchDel")
    public Object batchDel(String ids) {
        List<String> strings = Splitter.on(",").splitToList(ids);
        boolean b = baseKnowledgeInfoFacade.batchDel(strings);
        if (b) {
            return success("删除知识库成功");
        }
        return error("删除知识库失败");
    }

    @ApiOperation(value = "上传")
    @PostMapping(value = "/uploadFile")
    public Object uploadFile(@RequestParam("id") Long id, @RequestParam("filename") String filename) {
        boolean b = baseKnowledgeInfoFacade.uploadFile(id, filename);
        if (b) {
            return success("上传成功");
        }
        return error("上传失败");
    }

}
