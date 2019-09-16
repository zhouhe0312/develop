package cn.powertime.iatp.facade.web;

import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoSearchVo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoVo;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.resp.web.CategoryVo;
import cn.powertime.iatp.vo.resp.web.KnowLedgeVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface KnowledgeInfoFacade {

    List<CategoryVo> categoryList(Long typeId, String key);

    Page<KnowLedgeVo> list(ParamPageVo<BaseKnowledgeInfoSearchVo> vo);

    /**
     * C端查询知识库简介
     *
     * @param id
     * @return
     */
    BaseKnowledgeInfoVo selectWebById(Long id);

}
