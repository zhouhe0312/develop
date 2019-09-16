package cn.powertime.iatp.admin.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.entity.BaseTopicType;
import cn.powertime.iatp.facade.admin.BaseTopicTypeFacade;
import cn.powertime.iatp.vo.req.admin.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Splitter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 题库表 前端控制器
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@RestController
@RequestMapping("/base-topic-type")
@Api(value = "/base-topic-type",tags = "题库接口",produces = MediaType.ALL_VALUE)
public class BaseTopicTypeController extends BaseController {
    
    @Autowired
    private BaseTopicTypeFacade baseTopicTypeFacade;

    @ApiOperation(value = "新增题库",notes ="")
    @PostMapping(value = "/add")
    public Object add(@Validated @RequestBody BaseTopicTypeAddVo vo){
        boolean insert = baseTopicTypeFacade.add(vo);
        if (insert){
            return success("新增题库成功");
        }
        return error("新增题库失败");
    }

    @ApiOperation(value = "验证名称是否重复",notes ="")
    @PostMapping(value = "/checkNameOnly")
    public Object checkNameOnly(@RequestParam @NotBlank(message = "题库名称不能为空") String title, Long id){
        boolean insert = baseTopicTypeFacade.checkNameOnly(title,id);
        if (insert){
            return success("题库名称可用");
        }
        return error("题库名称已存在用");
    }

    @ApiOperation(value = "修改题库",notes ="")
    @PostMapping(value = "/edit")
    public Object edit(@Validated @RequestBody BaseTopicTypeEditVo vo){
        boolean b = baseTopicTypeFacade.edit(vo);
        if (b){
            return success("修改题库成功");
        }
        return error("修改题库失败");
    }

    @ApiOperation(value = "题库分页列表",notes ="")
    @PostMapping(value = "/list")
    public Object list(@Validated @RequestBody ParamPageVo<BaseTopicTypeSearchVo> vo) {
        Page<BaseTopicType> list = baseTopicTypeFacade.list(vo);
        return success(list);
    }

    @ApiOperation(value = "题库列表",notes ="")
    @PostMapping(value = "/listAll")
    public Object listAll() {
        List<BaseTopicType> list = baseTopicTypeFacade.listAll();
        return success(list);
    }

    @ApiOperation(value = "题库详情",notes ="")
    @GetMapping(value = "/details/{id}")
    public Object details(@PathVariable("id") @NotNull(message = "题库ID不能为空") Long id){
        return success(baseTopicTypeFacade.selectById( id ));
    }

    @ApiOperation(value = "批量删除题库",notes ="")
    @PostMapping(value = "/batchDel")
    public Object batchDel(String ids){
        List<String> strings = Splitter.on(",").splitToList(ids);
        boolean b = baseTopicTypeFacade.batchDel(strings);
        if (b){
            return success("批量删除题库成功");
        }
        return error("批量删除题库失败");
    }

}
