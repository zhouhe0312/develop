package cn.powertime.iatp.facade.web.impl;

import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.facade.web.KnowledgeInfoFacade;
import cn.powertime.iatp.service.BaseKnowledgeInfoService;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoSearchVo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoVo;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.resp.web.CategoryVo;
import cn.powertime.iatp.vo.resp.web.KnowLedgeVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class KnowledgeInfoFacadeImpl implements KnowledgeInfoFacade {

    @Autowired
    private BaseKnowledgeInfoService baseKnowledgeInfoService;

    @Override
    public List<CategoryVo> categoryList(Long typeId, String key) {
        return baseKnowledgeInfoService.categoryList(typeId, key);
    }

    @Override
    public Page<KnowLedgeVo> list(ParamPageVo<BaseKnowledgeInfoSearchVo> vo) {
        Page<KnowLedgeVo> page = new Page<>(vo.getPage().getCurrent(), vo.getPage().getSize());
        return baseKnowledgeInfoService.selectPageWeb(page, vo.getParams());
    }

    @Override
    public BaseKnowledgeInfoVo selectWebById(Long id) {
        return baseKnowledgeInfoService.selectWebById(id);
    }

}
