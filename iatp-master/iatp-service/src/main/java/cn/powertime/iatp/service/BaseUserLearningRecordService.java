package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.BaseCourse;
import cn.powertime.iatp.entity.BaseUserLearningRecord;
import cn.powertime.iatp.vo.req.admin.UserLearningRecordReqListVo;
import cn.powertime.iatp.vo.resp.admin.UserLearningRecordRespListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户学习记录表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseUserLearningRecordService extends IService<BaseUserLearningRecord> {

    Page<UserLearningRecordRespListVo> selectPage(Page<UserLearningRecordReqListVo> page, UserLearningRecordReqListVo params);

    List<BaseCourse> webIndexCourseList(Integer type);

    boolean add(BaseUserLearningRecord record);

    boolean edit(BaseUserLearningRecord record);
}
