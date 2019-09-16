package cn.powertime.iatp.web.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.entity.BaseKnowledgeContent;
import cn.powertime.iatp.facade.web.KnowledgeContentFacade;
import cn.powertime.iatp.vo.resp.admin.CommonTree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 知识库内容 前端控制器
 * </p>
 *
 * @author yang xin
 * @date 2019-06-17
 */
@RestController
@RequestMapping("/knowledge_content")
@Api(value = "/knowledge_content", tags = "知识库内容接口", produces = MediaType.ALL_VALUE)
public class KnowledgeContentController extends BaseController {

    @Autowired
    private KnowledgeContentFacade knowledgeContentFacade;

    @ApiOperation(value = "知识库内容tree")
    @PostMapping("/tree")
    public Object tree(Long pid) {
        List<CommonTree> tree = knowledgeContentFacade.tree(pid);
        return success(tree);
    }

    @ApiOperation(value = "知识库内容列表")
    @PostMapping("/listAll")
    public Object listAll(Long pid) {
        List<BaseKnowledgeContent> tree = knowledgeContentFacade.listAll(pid);
        return success(tree);
    }

}
