package cn.powertime.iatp.admin.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.facade.admin.BaseExaminationPaperFacade;
import cn.powertime.iatp.vo.req.admin.BuildPaperList;
import cn.powertime.iatp.vo.req.admin.SynthesizeBuildVo;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 试卷试题表 前端控制器
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@RestController
@RequestMapping("/base-examination-paper")
@Api(value = "/base-examination-paper",tags = "组卷接口",produces = MediaType.ALL_VALUE)
public class BaseExaminationPaperController extends BaseController {

    @Autowired
    private BaseExaminationPaperFacade baseExaminationPaperFacade;

    @ApiOperation(value = "组卷",notes ="")
    @PostMapping(value = "/build")
    public Object add(@Valid @RequestBody BuildPaperList list){
        boolean insert = baseExaminationPaperFacade.build(list);
        if (insert){
            return success("组卷成功");
        }
        return error("组卷失败");
    }

    @ApiOperation(value = "综合组卷",notes ="")
    @PostMapping(value = "/synthesizeBuild")
    public Object synthesizeBuild(@Valid @RequestBody SynthesizeBuildVo vo){
        boolean insert = baseExaminationPaperFacade.synthesizeBuild(vo);
        if (insert){
            return success("组卷成功");
        }
        return error("组卷失败");
    }

    @ApiOperation(value = "判断试卷是否已经组卷,true已经组卷了，false还没有")
    @GetMapping("/istest-assembly/{testPaperId}")
    public Object isTestAssembly(@PathVariable("testPaperId") Long testPaperId) {
        boolean testAssembly = baseExaminationPaperFacade.isTestAssembly(testPaperId);
        return success(ImmutableMap.of("testAssembly",testAssembly));
    }

}
