package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.BaseUserCollect;
import cn.powertime.iatp.mapper.BaseUserCollectMapper;
import cn.powertime.iatp.service.BaseUserCollectService;
import cn.powertime.iatp.vo.req.admin.UserCollectReqListVo;
import cn.powertime.iatp.vo.resp.admin.UserCollectRespListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户收藏表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Service
public class BaseUserCollectServiceImpl extends ServiceImpl<BaseUserCollectMapper, BaseUserCollect> implements BaseUserCollectService {

    @Override
    public Page<UserCollectRespListVo> selectPage(Page<UserCollectReqListVo> page, UserCollectReqListVo params) {
        return this.baseMapper.selectPage(page,params);
    }

    @Override
    public boolean add(BaseUserCollect collect) {
        return save(collect);
    }
}
