package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.BaseKnowledgeInfo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoSearchVo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoVo;
import cn.powertime.iatp.vo.resp.web.CategoryVo;
import cn.powertime.iatp.vo.resp.web.KnowLedgeVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 知识表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseKnowledgeInfoService extends IService<BaseKnowledgeInfo> {

    List<BaseKnowledgeInfo> webIndexKnowledgeList();

    boolean add(BaseKnowledgeInfo info);

    boolean edit(BaseKnowledgeInfo info);

    Page<BaseKnowledgeInfo> selectPage(Page<BaseKnowledgeInfo> page, BaseKnowledgeInfoSearchVo params);

    BaseKnowledgeInfo selectById(Long id);

    boolean batchDel(List<String> strings);

    /**
     * 统计所有类别的知识库条目
     *
     * @param typeId
     * @param key
     * @return
     */
    List<CategoryVo> categoryList(Long typeId, String key);

    /**
     * C端查询
     *
     * @param page
     * @param params
     * @return
     */
    Page<KnowLedgeVo> selectPageWeb(Page<KnowLedgeVo> page, BaseKnowledgeInfoSearchVo params);

    /**
     * C端查询知识库简介
     *
     * @param id
     * @return
     */
    BaseKnowledgeInfoVo selectWebById(Long id);

}
