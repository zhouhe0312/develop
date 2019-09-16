package cn.powertime.iatp.web.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.entity.BaseCourse;
import cn.powertime.iatp.entity.BaseKnowledgeInfo;
import cn.powertime.iatp.facade.admin.BaseChapelTestFacade;
import cn.powertime.iatp.facade.web.IndexFacade;
import cn.powertime.iatp.vo.resp.web.IndexCourseList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 首页 前端控制器
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@RestController
@RequestMapping("/index")
@Api(value = "/index",tags = "首页接口",produces = MediaType.ALL_VALUE)
public class IndexController extends BaseController {

    @Autowired
    private IndexFacade indexFacade;


    @ApiOperation(value = "知识库列表")
    @PostMapping("/knowledge")
    public Object knowledgeList() {
        List<BaseKnowledgeInfo> list = indexFacade.knowledgeList();
        return success(list);
    }

    @ApiOperation(value = "课程实验列表")
    @GetMapping("/courseList/{type}")
    public Object courseList(@ApiParam(name="type",value = "类型 1：课程  2：实验") @PathVariable("type") @NotNull(message = "类型不能为空") Integer type) {
        List<IndexCourseList> list = indexFacade.courseList(type);
        return success(list);
    }



}
