package cn.powertime.iatp.web.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.facade.web.CourseFacade;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.web.CoursePageListSearchVo;
import cn.powertime.iatp.vo.req.web.RecordVo;
import cn.powertime.iatp.vo.req.web.ResourceSearchVo;
import cn.powertime.iatp.vo.resp.web.CourseResourceRespListVo;
import cn.powertime.iatp.vo.resp.web.ExperimentResourceRespListVo;
import cn.powertime.iatp.vo.resp.web.IndexCourseList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 课程/实验 前端控制器
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@RestController
@RequestMapping("/course")
@Api(value = "/course",tags = "课程/实验接口",produces = MediaType.ALL_VALUE)
public class CourseController extends BaseController {


    @Autowired
    private CourseFacade courseFacade;

    @ApiOperation(value = "课程实验分页列表")
    @PostMapping("/courseList")
    public Object courseList(@Validated @RequestBody @ApiParam(name="查询参数",required=true)ParamPageVo<CoursePageListSearchVo> vo) {
        Page<IndexCourseList> list = courseFacade.selectPage(vo);
        return success(list);
    }

    @ApiOperation(value = "增加浏览量")
    @GetMapping("/addPv/{id}")
    public Object pvAdd(@PathVariable("id") @NotNull(message = "课程/实验ID不能为空") @ApiParam(name="id",value = "课程/实验ID")Long id) {
        return success(courseFacade.pvAdd(id));
    }

    @ApiOperation(value = "课程/实验详情")
    @GetMapping("/details/{id}")
    public Object details(@PathVariable("id") @NotNull(message = "课程/实验ID不能为空") @ApiParam(name="id",value = "课程/实验ID")Long id) {
        return success(courseFacade.details(id));
    }

    @ApiOperation(value = "学习进度打点")
    @PostMapping("/record")
    public Object record(@Validated @RequestBody RecordVo vo) {
        boolean b = courseFacade.record(vo);
        if(b){
            return success("记录成功");
        }
        return error("记录失败");
    }

    @ApiOperation(value = "章节列表")
    @GetMapping("/chapterList/{id}/{uid}")
    public Object chapterList(@PathVariable("id") @NotNull(message = "课程/实验ID不能为空") @ApiParam(name="id",value = "课程/实验ID")Long id,
    @PathVariable("uid") @NotNull(message = "用户ID不能为空") @ApiParam(name="uid",value = "用户ID")Long uid) {
        return success(courseFacade.chapterList(id,uid));
    }

    @ApiOperation(value = "课程资源列表")
    @PostMapping("/courseResourceList")
    public Object courseResourceList(@Validated @RequestBody ResourceSearchVo vo) {
        CourseResourceRespListVo list = courseFacade.courseResourceList(vo);
        return success(list);
    }

    @ApiOperation(value = "实验资源列表")
    @PostMapping("/experimentResourceList")
    public Object experimentResourceList(@Validated @RequestBody ResourceSearchVo vo) {
        List<ExperimentResourceRespListVo> list = courseFacade.experimentResourceList(vo);
        return success(list);
    }

    @ApiOperation(value = "实训")
    @GetMapping("/ractice")
    public void ractice(@RequestParam @NotNull(message = "参数不能为空") String data, HttpServletRequest request , HttpServletResponse response) throws Exception {
        //courseFacade.ractice(data);

    }



}
