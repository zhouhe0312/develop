package cn.powertime.iatp.admin.controller;


import cn.powertime.iatp.core.base.BaseController;
import cn.powertime.iatp.facade.admin.BaseUserCollectFacade;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.UserCollectReqListVo;
import cn.powertime.iatp.vo.resp.admin.UserCollectRespListVo;
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
 * 用户收藏表 前端控制器
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Api(value = "/base-user-collect",tags = {"用户收藏表 前端控制器"})
@RestController
@RequestMapping("/base-user-collect")
public class BaseUserCollectController extends BaseController {

    @Autowired
    private BaseUserCollectFacade baseUserCollectFacade;

    @ApiOperation(value = "用户收藏列表")
    @PostMapping("/list")
    public Object list(@Validated @RequestBody ParamPageVo<UserCollectReqListVo> vo) {
        Page<UserCollectRespListVo> respListVoPage = baseUserCollectFacade.list(vo);
        return success(respListVoPage);
    }

    @ApiOperation(value = "单个或者批量删除")
    @PostMapping("/delete")
    public Object deleteBatch(@RequestBody List<Long> ids) {
        boolean result = baseUserCollectFacade.delete(ids);
        if(result) {
            return success("删除成功");
        } else {
            return error("删除失败");
        }
    }
}
