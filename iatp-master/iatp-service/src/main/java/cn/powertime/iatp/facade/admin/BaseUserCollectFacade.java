package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.UserCollectReqListVo;
import cn.powertime.iatp.vo.resp.admin.UserCollectRespListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 用户收藏表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseUserCollectFacade {

    /**
     * 用户收藏试卷列表
     * @param vo 收藏Vo
     * @return 返回符合条件的数据
     */
    Page<UserCollectRespListVo> list(ParamPageVo<UserCollectReqListVo> vo);

    boolean delete(List<Long> ids);
}
