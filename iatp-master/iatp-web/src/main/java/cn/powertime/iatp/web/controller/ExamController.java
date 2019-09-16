package cn.powertime.iatp.web.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.facade.web.ExamFacade;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.web.CourseExamSeachVo;
import cn.powertime.iatp.vo.req.web.SubmitPaperVo;
import cn.powertime.iatp.vo.req.web.WrongQuestionsSeachVo;
import cn.powertime.iatp.vo.resp.web.CourseExamPageListVo;
import cn.powertime.iatp.vo.resp.web.PaperDetailsVo;
import cn.powertime.iatp.vo.resp.web.SubmitPaperRespVo;
import cn.powertime.iatp.vo.resp.web.WrongQuestionsPageListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 课程/实验 考核 前端控制器
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@RestController
@RequestMapping("/exam")
@Api(value = "/exam",tags = "课程/实验 考核 接口",produces = MediaType.ALL_VALUE)
public class ExamController extends BaseController {

    @Autowired
    private ExamFacade examFacade;

    @ApiOperation(value = "课程/实验 考核分页列表")
    @PostMapping("/webCourseExamList")
    public Object webCourseExamList(@Validated @RequestBody ParamPageVo<CourseExamSeachVo> vo) {
        Page<CourseExamPageListVo> list =examFacade.webCourseExamList(vo);
        return success(list);
    }

    @ApiOperation(value = "收藏/取消收藏接口")
    @GetMapping("/collect/{id}/{type}")
    public Object collect(@PathVariable("id") @NotNull(message = "试卷ID不能为空")@ApiParam(name="id",value = "收藏课程/实验ID") Long id, @PathVariable("type") @NotNull(message = "类型不能为空 1：收藏  2：取消收藏") @ApiParam(name="type",value = "类型 1：收藏  2：取消收藏")Integer type) {
        boolean b = examFacade.collect(id , type);
        if(b){
            return success("操作成功");
        }
        return error("操作失败");
    }

    @ApiOperation(value = "开始考核试卷列表")
    @GetMapping("/paperDetail/{id}")
    public Object paperDetail(@PathVariable("id") @NotNull(message = "试卷ID不能为空") @ApiParam(name="id",value = "试卷ID")Long id) {
        PaperDetailsVo b = examFacade.paperDetail(id);
        return success(b );
    }

    @ApiOperation(value = "查看考核试卷列表")
    @GetMapping("/paperResultDetail/{id}/{resultId}")
    public Object paperResultDetail(@PathVariable("id") @NotNull(message = "试卷ID不能为空")@ApiParam(name="id",value = "试卷ID") Long id,@PathVariable("resultId") @NotNull(message = "考试结果ID不能为空") @ApiParam(name="resultId",value = "考试结果ID")Long resultId) {
        PaperDetailsVo b = examFacade.paperResultDetail(id,resultId);
        return success(b );
    }


    @ApiOperation(value = "提交试卷")
    @PostMapping("/submitPaper")
    public Object submitPaper(@Valid @RequestBody SubmitPaperVo vo) {
        SubmitPaperRespVo list =examFacade.submitPaper(vo);
        return success(list);
    }

    @ApiOperation(value = "错题分页列表")
    @PostMapping("/wrongList")
    public Object wrongList(@Validated @RequestBody ParamPageVo<WrongQuestionsSeachVo> vo) {
        Page<WrongQuestionsPageListVo> list =examFacade.wrongList(vo);
        return success(list);
    }



}
