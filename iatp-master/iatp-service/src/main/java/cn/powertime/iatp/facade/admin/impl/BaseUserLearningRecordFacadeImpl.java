package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.IatpConstants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.BaseUserLearningRecord;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.BaseUserLearningRecordFacade;
import cn.powertime.iatp.service.BaseUserLearningRecordService;
import cn.powertime.iatp.utils.CollectionUtils;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.UserLearningRecordReqListVo;
import cn.powertime.iatp.vo.resp.admin.UserLearningRecordRespListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户学习记录表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
public class BaseUserLearningRecordFacadeImpl implements BaseUserLearningRecordFacade {

    @Autowired
    private BaseUserLearningRecordService baseUserLearningRecordService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean delete(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            throw new IatpException("传入的ID为空！");
        }
        Collection<BaseUserLearningRecord> learningRecords = baseUserLearningRecordService.listByIds(ids);
        Set<String> set = Sets.newHashSet();
        learningRecords.forEach(item -> {
            item.setStatus(Constants.STATUS_DEL);
            set.add(String.valueOf(item.getUserId())+item.getResourceId());
        });
        redisTemplate.boundSetOps(IatpConstants.RECORD_KEY).remove(set.toArray());
        return baseUserLearningRecordService.updateBatchById(learningRecords);
    }

    @Override
    public Page<UserLearningRecordRespListVo> list(ParamPageVo<UserLearningRecordReqListVo> vo) {
        Page<UserLearningRecordReqListVo> page = new Page<>(vo.getPage().getCurrent(),vo.getPage().getSize());
        return baseUserLearningRecordService.selectPage(page,vo.getParams());
    }
}
