package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.entity.BaseKnowledgeInfo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoAddVo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoEditVo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoSearchVo;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 知识表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseKnowledgeInfoFacade {

    /**
     * 验证名称是否存在
     *
     * @param name
     * @param id
     * @return
     */
    boolean checkNameOnly(String name, Long id);

    boolean add(BaseKnowledgeInfoAddVo vo);

    boolean edit(BaseKnowledgeInfoEditVo vo);

    Page<BaseKnowledgeInfo> list(ParamPageVo<BaseKnowledgeInfoSearchVo> vo);

    List<BaseKnowledgeInfo> listAll();

    BaseKnowledgeInfo selectById(Long id);

    boolean batchDel(List<String> strings);

    boolean uploadFile(Long id, String filename);

}
