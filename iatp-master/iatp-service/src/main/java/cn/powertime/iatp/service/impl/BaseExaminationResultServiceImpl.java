package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.BaseExaminationResult;
import cn.powertime.iatp.mapper.BaseExaminationResultMapper;
import cn.powertime.iatp.service.BaseExaminationResultService;
import cn.powertime.iatp.vo.req.admin.ExaminationResultReqVo;
import cn.powertime.iatp.vo.resp.admin.ExaminationResultRespVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户考试结果表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Service
public class BaseExaminationResultServiceImpl extends ServiceImpl<BaseExaminationResultMapper, BaseExaminationResult> implements BaseExaminationResultService {

    @Override
    public Page<ExaminationResultRespVo> selectPage(Page<ExaminationResultReqVo> page, ExaminationResultReqVo params) {
        return this.baseMapper.selectPage(page,params);
    }

    @Override
    public void add(BaseExaminationResult baseExaminationResult) {
        save(baseExaminationResult);
    }
}
