package cn.powertime.iatp.admin.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.facade.admin.BaseWrongQuestionsFacade;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.WrongQuestionsReqListVo;
import cn.powertime.iatp.vo.resp.admin.WrongQuestionsRespListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 错题表 前端控制器
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Api(value = "/base-wrong-questions",tags = {"错题表 前端控制器"})
@RestController
@RequestMapping("/base-wrong-questions")
public class BaseWrongQuestionsController extends BaseController {

    @Autowired
    private BaseWrongQuestionsFacade baseWrongQuestionsFacade;

    @ApiOperation(value = "错题列表")
    @PostMapping("/list")
    public Object list(@Validated @RequestBody ParamPageVo<WrongQuestionsReqListVo> vo) {
        Page<WrongQuestionsRespListVo> respListVoPage = baseWrongQuestionsFacade.list(vo);
        return success(respListVoPage);
    }

    @ApiOperation(value = "单个或者批量删除")
    @PostMapping("/delete")
    public Object deleteBatch(@RequestBody List<Long> ids) {
        boolean result = baseWrongQuestionsFacade.delete(ids);
        if(result) {
            return success("删除成功");
        } else {
            return error("删除失败");
        }
    }

}
