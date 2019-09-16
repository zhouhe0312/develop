package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.BaseUserCollect;
import cn.powertime.iatp.vo.req.admin.UserCollectReqListVo;
import cn.powertime.iatp.vo.resp.admin.UserCollectRespListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户收藏表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseUserCollectService extends IService<BaseUserCollect> {

    /**
     *
     * @param page
     * @param params
     * @return
     */
    Page<UserCollectRespListVo> selectPage(Page<UserCollectReqListVo> page, UserCollectReqListVo params);

    boolean add(BaseUserCollect collect);
}
