package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.BaseExaminationResult;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.BaseExaminationResultFacade;
import cn.powertime.iatp.logging.EnumLogType;
import cn.powertime.iatp.logging.Logging;
import cn.powertime.iatp.service.BaseExaminationResultService;
import cn.powertime.iatp.utils.CollectionUtils;
import cn.powertime.iatp.vo.req.admin.ExaminationResultReqVo;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.resp.admin.ExaminationResultRespVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户考试结果表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
@Order(2)
public class BaseExaminationResultFacadeImpl implements BaseExaminationResultFacade {

    @Autowired
    private BaseExaminationResultService baseExaminationResultService;

    @Override
    @Logging(code = "result.del",vars = {"","ids"},type = EnumLogType.DEL)
    public boolean delete(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            throw new IatpException("传入的ID为空！");
        }
        List<BaseExaminationResult> examinationResults = baseExaminationResultService.listByIds(ids).stream().map(item -> {
            item.setStatus(Constants.STATUS_DEL);
            return item;
        }).collect(Collectors.toList());
        return baseExaminationResultService.updateBatchById(examinationResults);
    }

    @Override
    public Page<ExaminationResultRespVo> list(ParamPageVo<ExaminationResultReqVo> vo) {
        Page<ExaminationResultReqVo> page = new Page<>(vo.getPage().getCurrent(),vo.getPage().getSize());
        return baseExaminationResultService.selectPage(page,vo.getParams());
    }
}
