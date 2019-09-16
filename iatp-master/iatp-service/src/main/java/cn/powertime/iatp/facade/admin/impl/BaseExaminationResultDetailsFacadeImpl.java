package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.facade.admin.BaseExaminationResultDetailsFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户考试结果详情表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
public class BaseExaminationResultDetailsFacadeImpl implements BaseExaminationResultDetailsFacade {

}
