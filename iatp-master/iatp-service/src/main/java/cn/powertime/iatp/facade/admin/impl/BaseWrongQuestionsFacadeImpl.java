package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.BaseWrongQuestions;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.BaseWrongQuestionsFacade;
import cn.powertime.iatp.service.BaseWrongQuestionsService;
import cn.powertime.iatp.utils.CollectionUtils;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.WrongQuestionsReqListVo;
import cn.powertime.iatp.vo.resp.admin.WrongQuestionsRespListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 错题表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
public class BaseWrongQuestionsFacadeImpl implements BaseWrongQuestionsFacade {

    @Autowired
    private BaseWrongQuestionsService baseWrongQuestionsService;

    @Override
    public Page<WrongQuestionsRespListVo> list(ParamPageVo<WrongQuestionsReqListVo> vo) {
        Page<WrongQuestionsRespListVo> page = new Page<>(vo.getPage().getCurrent(),vo.getPage().getSize());
        return baseWrongQuestionsService.selectPage(page,vo.getParams());
    }

    @Override
    public boolean delete(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            throw new IatpException("传入的ID为空！");
        }
        Collection<BaseWrongQuestions> wrongQuestions = baseWrongQuestionsService.listByIds(ids);
        List<BaseWrongQuestions> questions = wrongQuestions.stream().map(item -> {
            item.setStatus(Constants.STATUS_DEL);
            return item;
        }).collect(Collectors.toList());
        return baseWrongQuestionsService.updateBatchById(questions);
    }
}
