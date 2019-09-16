package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.UserLearningRecordReqListVo;
import cn.powertime.iatp.vo.resp.admin.UserLearningRecordRespListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 用户学习记录表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseUserLearningRecordFacade {

    boolean delete(List<Long> ids);

    Page<UserLearningRecordRespListVo> list(ParamPageVo<UserLearningRecordReqListVo> vo);
}
