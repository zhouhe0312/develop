package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.BaseExaminationResult;
import cn.powertime.iatp.vo.req.admin.ExaminationResultReqVo;
import cn.powertime.iatp.vo.resp.admin.ExaminationResultRespVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户考试结果表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseExaminationResultService extends IService<BaseExaminationResult> {

    Page<ExaminationResultRespVo> selectPage(Page<ExaminationResultReqVo> page, ExaminationResultReqVo params);

    void add(BaseExaminationResult baseExaminationResult);
}
