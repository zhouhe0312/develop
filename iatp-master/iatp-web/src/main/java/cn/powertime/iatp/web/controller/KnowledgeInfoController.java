package cn.powertime.iatp.web.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.facade.web.KnowledgeInfoFacade;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoSearchVo;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.resp.web.CategoryVo;
import cn.powertime.iatp.vo.resp.web.KnowLedgeVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 知识库 前端控制器
 * </p>
 *
 * @author yang xin
 * @date 2019-06-17
 */
@RestController
@RequestMapping("/knowledge_info")
@Api(value = "/knowledge_info", tags = "知识库接口", produces = MediaType.ALL_VALUE)
public class KnowledgeInfoController extends BaseController {

    @Autowired
    private KnowledgeInfoFacade knowledgeInfoFacade;

    @ApiOperation(value = "知识库类别分类列表")
    @PostMapping("/categoryList")
    public Object categoryList(@RequestParam Long typeId, String key) {
        List<CategoryVo> list = knowledgeInfoFacade.categoryList(typeId, key);
        return success(list);
    }

    @ApiOperation(value = "知识库分页列表")
    @PostMapping(value = "/list")
    public Object list(@Validated @RequestBody ParamPageVo<BaseKnowledgeInfoSearchVo> vo) {
        Page<KnowLedgeVo> list = knowledgeInfoFacade.list(vo);
        return success(list);
    }

    @ApiOperation(value = "详情")
    @GetMapping(value = "/details")
    public Object details(@RequestParam Long id) {
        return success(knowledgeInfoFacade.selectWebById(id));
    }

}
