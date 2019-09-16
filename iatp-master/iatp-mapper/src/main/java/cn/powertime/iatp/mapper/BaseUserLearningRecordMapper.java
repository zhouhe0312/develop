package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.BaseCourse;
import cn.powertime.iatp.entity.BaseUserLearningRecord;
import cn.powertime.iatp.vo.req.admin.UserLearningRecordReqListVo;
import cn.powertime.iatp.vo.resp.admin.UserLearningRecordRespListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户学习记录表 Mapper 接口
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Mapper
public interface BaseUserLearningRecordMapper extends BaseMapper<BaseUserLearningRecord> {

    Page<UserLearningRecordRespListVo> selectPage(Page<UserLearningRecordReqListVo> page,@Param("vo") UserLearningRecordReqListVo vo);

    List<BaseCourse> webIndexCourseList(@Param("type") Integer type);
}
