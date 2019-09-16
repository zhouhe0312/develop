package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.vo.req.admin.ExaminationResultReqVo;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.resp.admin.ExaminationResultRespVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 用户考试结果表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseExaminationResultFacade {

    boolean delete(List<Long> ids);

    Page<ExaminationResultRespVo> list(ParamPageVo<ExaminationResultReqVo> vo);
}
