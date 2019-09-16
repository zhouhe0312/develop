package cn.powertime.iatp.admin.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.facade.admin.SysLogFacade;
import cn.powertime.iatp.utils.excel.EasyExcelUtils;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.SysLogListVo;
import cn.powertime.iatp.vo.resp.admin.SysLogExportVo;
import cn.powertime.iatp.vo.resp.admin.SysLogPageListVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author liqi
 * @since 2019-04-19
 */
@RestController
@RequestMapping(value = "/sysLog")
@Api(value = "/sysLog",tags = "系统日志接口",produces = MediaType.ALL_VALUE)
public class SysLogController extends BaseController {

    @Autowired
    private SysLogFacade sysLogFacade;

    @ApiOperation(value = "日志列表",notes ="")
    @PostMapping("/list")
    public Object list(@Validated @RequestBody ParamPageVo<SysLogListVo> vo){
        IPage<SysLogPageListVo> list = sysLogFacade.list(vo);
        return success(list);
    }

    @ApiOperation(value = "日志详情",notes ="")
    @GetMapping(value = "/details/{id}")
    public Object details(@PathVariable("id") @NotNull(message = "日志ID不能为空") Long id){
        return success(sysLogFacade.findById( id ));
    }

    @ApiOperation(value = "日志打印",notes ="")
    @PostMapping(value = "/import")
    public void imports(@RequestBody SysLogListVo vo, HttpServletResponse response) throws IOException {
        List<SysLogExportVo> list = sysLogFacade.export(vo);

        EasyExcelUtils.generateExcel(response,list,"日志打印", SysLogExportVo.class);
    }

    @ApiOperation(value = "单个或者批量删除")
    @PostMapping("/delete")
    public Object deleteBatch(@RequestBody List<Long> ids) {
        boolean result = sysLogFacade.delete(ids);
        if(result) {
            return success("删除成功");
        } else {
            return error("删除失败");
        }
    }



}
