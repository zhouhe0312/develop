package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.BaseKnowledgeInfo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoSearchVo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoVo;
import cn.powertime.iatp.vo.resp.web.CategoryVo;
import cn.powertime.iatp.vo.resp.web.KnowLedgeVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 知识表 Mapper 接口
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Mapper
public interface BaseKnowledgeInfoMapper extends BaseMapper<BaseKnowledgeInfo> {

    List<BaseKnowledgeInfo> webIndexKnowledgeList();

    Page<BaseKnowledgeInfo> mySelectPage(Page page, @Param("vo") BaseKnowledgeInfoSearchVo params);

    Integer batchDel(@Param("ids") List<String> strings);

    /**
     * 统计所有类别的知识库条目
     *
     * @param typeId
     * @param key
     * @return
     */
    List<CategoryVo> categoryList(@Param("typeId") Long typeId, @Param("key") String key);

    /**
     * C端查询
     *
     * @param page
     * @param params
     * @return
     */
    Page<KnowLedgeVo> mySelectPageWeb(Page page, @Param("vo") BaseKnowledgeInfoSearchVo params);

    /**
     * C端查询知识库简介
     *
     * @param id
     * @return
     */
    BaseKnowledgeInfoVo selectWebById(@Param("id") Long id);

}
