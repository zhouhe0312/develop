package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.BaseUserCollectFacade;
import cn.powertime.iatp.service.BaseUserCollectService;
import cn.powertime.iatp.utils.CollectionUtils;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.UserCollectReqListVo;
import cn.powertime.iatp.vo.resp.admin.UserCollectRespListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户收藏表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
public class BaseUserCollectFacadeImpl implements BaseUserCollectFacade {

    @Autowired
    private BaseUserCollectService baseUserCollectService;

    @Override
    public Page<UserCollectRespListVo> list(ParamPageVo<UserCollectReqListVo> vo) {
        Page<UserCollectReqListVo> page = new Page<>(vo.getPage().getCurrent(),vo.getPage().getSize());
        return baseUserCollectService.selectPage(page,vo.getParams());
    }

    @Override
    public boolean delete(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            throw new IatpException("传入的ID为空！");
        }
        return baseUserCollectService.removeByIds(ids);
    }
}
