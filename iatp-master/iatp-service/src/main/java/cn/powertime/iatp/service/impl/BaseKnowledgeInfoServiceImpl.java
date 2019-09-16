package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.BaseKnowledgeInfo;
import cn.powertime.iatp.mapper.BaseKnowledgeInfoMapper;
import cn.powertime.iatp.service.BaseKnowledgeInfoService;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoSearchVo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoVo;
import cn.powertime.iatp.vo.resp.web.CategoryVo;
import cn.powertime.iatp.vo.resp.web.KnowLedgeVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 知识表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Service
public class BaseKnowledgeInfoServiceImpl extends ServiceImpl<BaseKnowledgeInfoMapper, BaseKnowledgeInfo> implements BaseKnowledgeInfoService {

    @Autowired
    private BaseKnowledgeInfoMapper baseKnowledgeInfoMapper;

    @Override
    public List<BaseKnowledgeInfo> webIndexKnowledgeList() {
        return baseMapper.webIndexKnowledgeList();
    }

    @Override
    public boolean add(BaseKnowledgeInfo info) {
        return save(info);
    }

    @Override
    public boolean edit(BaseKnowledgeInfo info) {
        return updateById(info);
    }

    @Override
    public Page<BaseKnowledgeInfo> selectPage(Page<BaseKnowledgeInfo> page, BaseKnowledgeInfoSearchVo params) {
        return baseKnowledgeInfoMapper.mySelectPage(page, params);
    }

    @Override
    public BaseKnowledgeInfo selectById(Long id) {
        return getById(id);
    }

    @Override
    public boolean batchDel(List<String> strings) {
        Integer i = baseKnowledgeInfoMapper.batchDel(strings);
        return i > 0;
    }

    @Override
    public List<CategoryVo> categoryList(Long typeId, String  key) {
        return baseKnowledgeInfoMapper.categoryList(typeId, key);
    }

    @Override
    public Page<KnowLedgeVo> selectPageWeb(Page<KnowLedgeVo> page, BaseKnowledgeInfoSearchVo params) {
        return baseKnowledgeInfoMapper.mySelectPageWeb(page, params);
    }

    @Override
    public BaseKnowledgeInfoVo selectWebById(Long id) {
        return baseKnowledgeInfoMapper.selectWebById(id);
    }

}
