package cn.powertime.iatp.admin.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.entity.BaseKnowledgeContent;
import cn.powertime.iatp.enums.HttpStatus;
import cn.powertime.iatp.exception.FileServerException;
import cn.powertime.iatp.facade.admin.BaseKnowledgeContentFacade;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeContentAddVo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeContentEditVo;
import cn.powertime.iatp.vo.resp.admin.CommonTree;
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
 * 知识内容表 前端控制器
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Api(value = "/base-knowledge-content", tags = {"知识库内容建设接口"})
@RestController
@RequestMapping("/base-knowledge-content")
public class BaseKnowledgeContentController extends BaseController {

    @Autowired
    private BaseKnowledgeContentFacade baseKnowledgeContentFacade;

    @ApiOperation(value = "验证名称唯一性")
    @GetMapping("/checkNameOnly")
    public Object checkNameOnly(@RequestParam Long father, @RequestParam String name) {
        boolean b = baseKnowledgeContentFacade.checkNameOnly(father, name, null);
        if (b) {
            return error(HttpStatus.CHECK_FAIL, null, "名称已经存在");
        }
        return success("名称可用");
    }

    @ApiOperation(value = "新增知识库内容")
    @PostMapping(value = "/add")
    public Object add(@Validated @RequestBody BaseKnowledgeContentAddVo vo) {
        boolean insert = baseKnowledgeContentFacade.add(vo);
        if (insert) {
            return success("新增知识库内容成功");
        }
        return error("新增知识库内容失败");
    }

    @ApiOperation(value = "修改知识库内容")
    @PostMapping(value = "/edit")
    public Object edit(@Validated @RequestBody BaseKnowledgeContentEditVo vo) {
        boolean b = baseKnowledgeContentFacade.edit(vo);
        if (b) {
            return success("修改知识库内容成功");
        }
        return error("修改知识库内容失败");
    }

    @ApiOperation(value = "详情")
    @GetMapping(value = "/details/{id}")
    public Object details(@PathVariable("id") @NotNull(message = "ID不能为空") Long id) {
        return success(baseKnowledgeContentFacade.selectById(id));
    }

    @ApiOperation(value = "列表")
    @GetMapping(value = "/list/{pid}")
    public Object list(@PathVariable("pid") @NotNull(message = "pid不能为空") Long pid) {
        List<BaseKnowledgeContent> list = baseKnowledgeContentFacade.selectByPid(pid);
        return success(list);
    }

    @ApiOperation(value = "批量删除知识库内容")
    @PostMapping(value = "/batchDel")
    public Object batchDel(@RequestParam("ids") String ids) {
        List<String> strings = Splitter.on(",").splitToList(ids);
        boolean b = baseKnowledgeContentFacade.batchDel(strings);
        if (b) {
            return success("删除知识库内容成功");
        }
        return error("删除知识库内容失败");
    }

    @ApiOperation(value = "知识库内容tree")
    @PostMapping("/tree")
    public Object tree(String name, Long pid) {
        List<CommonTree> tree = baseKnowledgeContentFacade.tree(name, pid);
        return success(tree);
    }

    @ApiOperation(value = "上传图片")
    @PostMapping(value = "/uploadPicture")
    public Object uploadPicture(@RequestParam("id") Long id, @RequestParam("filename") String filename) {
        boolean b = baseKnowledgeContentFacade.uploadPicture(id, filename);
        if (b) {
            return success("上传成功");
        }
        return error("上传失败");
    }

    @ApiOperation(value = "上传内容")
    @PostMapping(value = "/uploadContent")
    public Object uploadContent(@RequestParam MultipartFile file, @RequestParam Long infoId) {
        if (file == null || file.isEmpty()) {
            throw new FileServerException("上传文件File为空！");
        }
        boolean b = baseKnowledgeContentFacade.uploadContent(file, infoId);
        if (b) {
            return success("导入成功");
        } else {
            return error("导入失败");
        }
    }

}
