package cn.powertime.iatp.admin.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.entity.BaseTopic;
import cn.powertime.iatp.exception.FileServerException;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.BaseTopicFacade;
import cn.powertime.iatp.vo.req.admin.BaseTopicAddVo;
import cn.powertime.iatp.vo.req.admin.BaseTopicEditVo;
import cn.powertime.iatp.vo.req.admin.BaseTopicSearchVo;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.resp.admin.BaseTopicPageListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Splitter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 试题表 前端控制器
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@RestController
@RequestMapping("/base-topic")
@Api(value = "/base-topic",tags = "试题管理接口",produces = MediaType.ALL_VALUE)
public class BaseTopicController extends BaseController {

    @Autowired
    private BaseTopicFacade baseTopicFacade;

    @ApiOperation(value = "新增试题",notes ="")
    @PostMapping(value = "/add")
    public Object add(@Validated @RequestBody BaseTopicAddVo vo){
        boolean insert = baseTopicFacade.add(vo);
        if (insert){
            return success("新增试题成功");
        }
        return error("新增试题失败");
    }

    @ApiOperation(value = "修改试题",notes ="")
    @PostMapping(value = "/edit")
    public Object edit(@Validated @RequestBody BaseTopicEditVo vo){
        boolean b = baseTopicFacade.edit(vo);
        if (b){
            return success("修改试题成功");
        }
        return error("修改试题失败");
    }

    @ApiOperation(value = "试题分页列表",notes ="")
    @PostMapping(value = "/list")
    public Object list(@Validated @RequestBody ParamPageVo<BaseTopicSearchVo> vo) {
        Page<BaseTopicPageListVo> list = baseTopicFacade.list(vo);
        return success(list);
    }

    @ApiOperation(value = "试题列表",notes ="")
    @PostMapping(value = "/listAll")
    public Object listAll(Long typeId) {
        List<BaseTopic> list = baseTopicFacade.listAll(typeId);
        return success(list);
    }

    @ApiOperation(value = "试题详情",notes ="")
    @GetMapping(value = "/details/{id}")
    public Object details(@PathVariable("id") @NotNull(message = "试题ID不能为空") Long id){
        return success(baseTopicFacade.selectById( id ));
    }

    @ApiOperation(value = "批量删除试题",notes ="")
    @PostMapping(value = "/batchDel")
    public Object batchDel(String ids){
        List<String> strings = Splitter.on(",").splitToList(ids);
        boolean b = baseTopicFacade.batchDel(strings);
        if (b){
            return success("批量删除试题成功");
        }
        return error("批量删除试题失败");
    }


    @ApiOperation(value = "启用禁用试题",notes ="")
    @PostMapping(value = "/enableDisabled/{id}")
    public Object enableDisabled(@PathVariable("id") @NotNull(message = "试题ID不能为空")  Long id){
        boolean b = baseTopicFacade.enableDisabled(String.valueOf(id));
        if (b){
            return success("操作成功");
        }
        return error("操作失败");
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入",notes = "")
    public Object upload(MultipartFile file, @NotNull(message = "题库ID不能为空")  Long typeId) {
        if(file == null || file.isEmpty()) {
            throw new IatpException("上传文件File为空！");
        }
        boolean b = baseTopicFacade.importExcel(file,typeId);
        if (b){
            return success("成功导入");
        }else{
            return error("导入失败");
        }
    }

}
