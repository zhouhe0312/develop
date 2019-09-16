package cn.powertime.iatp.admin.controller;

import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.facade.admin.BaseStandardFacade;
import cn.powertime.iatp.vo.req.admin.BaseStandardAddVo;
import cn.powertime.iatp.vo.req.admin.BaseStandardEditVo;
import com.google.common.base.Splitter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 知识库内容标准表接口
 * </p>
 *
 * @author yang xin
 * @since 2019-07-04
 */
@Api(value = "/base-standard", tags = {"知识库内容标准接口"})
@RestController
@RequestMapping("/base-standard")
public class BaseStandardController extends BaseController {

    @Autowired
    private BaseStandardFacade baseStandardFacade;

    @ApiOperation(value = "创建知识库内容标准")
    @PostMapping(value = "/add")
    public Object add(@Validated @RequestBody BaseStandardAddVo vo) {
        boolean insert = baseStandardFacade.add(vo);
        if (insert) {
            return success("创建成功");
        }
        return error("创建失败");
    }

    @ApiOperation(value = "修改知识库内容标准")
    @PostMapping(value = "/edit")
    public Object edit(@Validated @RequestBody BaseStandardEditVo vo) {
        boolean b = baseStandardFacade.edit(vo);
        if (b) {
            return success("修改成功");
        }
        return error("修改失败");
    }

    @ApiOperation(value = "知识库内容标准列表")
    @PostMapping(value = "/list")
    public Object list(Long controlId) {
        return success(baseStandardFacade.list(controlId));
    }

    @ApiOperation(value = "批量删除")
    @PostMapping(value = "/batchDel")
    public Object batchDel(@RequestParam("ids") String ids) {
        List<String> strings = Splitter.on(",").splitToList(ids);
        boolean b = baseStandardFacade.batchDel(strings);
        if (b) {
            return success("删除成功");
        }
        return error("删除失败");
    }

}
