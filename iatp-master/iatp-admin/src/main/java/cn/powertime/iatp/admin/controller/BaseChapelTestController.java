package cn.powertime.iatp.admin.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.entity.BaseChapelTest;
import cn.powertime.iatp.facade.admin.BaseChapelTestFacade;
import cn.powertime.iatp.vo.req.admin.BaseChapelTestAddVo;
import cn.powertime.iatp.vo.req.admin.BaseChapelTestEditVo;
import cn.powertime.iatp.vo.req.admin.BaseChapelTestSearchVo;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.resp.admin.BaseChapelTestPageListVo;
import cn.powertime.iatp.vo.resp.admin.KeyValueVo;
import cn.powertime.iatp.vo.resp.admin.TopicListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Splitter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 试卷表 前端控制器
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@RestController
@RequestMapping("/base-chapel-test")
@Api(value = "/base-chapel-test",tags = "试卷接口",produces = MediaType.ALL_VALUE)
public class BaseChapelTestController extends BaseController {

    @Autowired
    private BaseChapelTestFacade baseChapelTestFacade;


    @ApiOperation(value = "试卷select选择框列表")
    @GetMapping("/select/list")
    public Object selectList() {
        List<KeyValueVo> keyValueVos = baseChapelTestFacade.selectList();
        return success(keyValueVos);
    }

    @ApiOperation(value = "新增试卷",notes ="")
    @PostMapping(value = "/add")
    public Object add(@Validated @RequestBody BaseChapelTestAddVo vo){
        boolean insert = baseChapelTestFacade.add(vo);
        if (insert){
            return success("新增试卷成功");
        }
        return error("新增试卷失败");
    }

    @ApiOperation(value = "修改试卷",notes ="")
    @PostMapping(value = "/edit")
    public Object edit(@Validated @RequestBody BaseChapelTestEditVo vo){
        boolean b = baseChapelTestFacade.edit(vo);
        if (b){
            return success("修改试卷成功");
        }
        return error("修改试卷失败");
    }

    @ApiOperation(value = "试卷分页列表",notes ="")
    @PostMapping(value = "/list")
    public Object list(@Validated @RequestBody ParamPageVo<BaseChapelTestSearchVo> vo) {
        Page<BaseChapelTestPageListVo> list = baseChapelTestFacade.list(vo);
        return success(list);
    }

    @ApiOperation(value = "试卷列表",notes ="")
    @PostMapping(value = "/listAll")
    public Object listAll() {
        List<BaseChapelTest> list = baseChapelTestFacade.listAll();
        return success(list);
    }

    @ApiOperation(value = "试卷详情",notes ="")
    @GetMapping(value = "/details/{id}")
    public Object details(@PathVariable("id") @NotNull(message = "试卷ID不能为空") Long id){
        return success(baseChapelTestFacade.selectById( id ));
    }

    @ApiOperation(value = "批量删除试卷",notes ="")
    @PostMapping(value = "/batchDel")
    public Object batchDel(String ids){
        List<String> strings = Splitter.on(",").splitToList(ids);
        boolean b = baseChapelTestFacade.batchDel(strings);
        if (b){
            return success("批量删除试卷成功");
        }
        return error("批量删除试卷失败");
    }


    @ApiOperation(value = "启用禁用试卷",notes ="")
    @PostMapping(value = "/enableDisabled/{id}")
    public Object enableDisabled(@PathVariable("id") @NotNull(message = "试卷ID不能为空")  Long id){
        boolean b = baseChapelTestFacade.enableDisabled(String.valueOf(id));
        if (b){
            return success("操作成功");
        }
        return error("操作失败");
    }

    @ApiOperation(value = "根据试卷ID获取试题列表",notes ="")
    @GetMapping(value = "/getTopicListByChapelId/{id}")
    public Object getTopicListByChapelId(@PathVariable("id") @NotNull(message = "试卷ID不能为空")  Long id){
         List<TopicListVo> list= baseChapelTestFacade.getTopicListByChapelId(id);
         return success(list);
    }


}
