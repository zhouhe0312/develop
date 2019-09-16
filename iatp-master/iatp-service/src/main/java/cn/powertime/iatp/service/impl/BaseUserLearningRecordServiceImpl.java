package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.BaseCourse;
import cn.powertime.iatp.entity.BaseUserLearningRecord;
import cn.powertime.iatp.mapper.BaseUserLearningRecordMapper;
import cn.powertime.iatp.service.BaseUserLearningRecordService;
import cn.powertime.iatp.vo.req.admin.UserLearningRecordReqListVo;
import cn.powertime.iatp.vo.resp.admin.UserLearningRecordRespListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户学习记录表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Service
public class BaseUserLearningRecordServiceImpl extends ServiceImpl<BaseUserLearningRecordMapper, BaseUserLearningRecord> implements BaseUserLearningRecordService {

    @Override
    public Page<UserLearningRecordRespListVo> selectPage(Page<UserLearningRecordReqListVo> page, UserLearningRecordReqListVo params) {
        return this.baseMapper.selectPage(page,params);
    }

    @Override
    public List<BaseCourse> webIndexCourseList(Integer type) {
        return this.baseMapper.webIndexCourseList(type);
    }

    @Override
    public boolean add(BaseUserLearningRecord record) {
        return save(record);
    }

    @Override
    public boolean edit(BaseUserLearningRecord record) {
        return updateById(record);
    }
}
