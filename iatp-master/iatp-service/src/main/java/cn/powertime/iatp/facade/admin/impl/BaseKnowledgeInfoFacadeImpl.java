package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.BaseKnowledgeContent;
import cn.powertime.iatp.entity.BaseKnowledgeInfo;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.BaseKnowledgeInfoFacade;
import cn.powertime.iatp.logging.EnumLogType;
import cn.powertime.iatp.logging.Logging;
import cn.powertime.iatp.service.BaseKnowledgeContentService;
import cn.powertime.iatp.service.BaseKnowledgeInfoService;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoAddVo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoEditVo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeInfoSearchVo;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 知识表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
@Order(2)
public class BaseKnowledgeInfoFacadeImpl implements BaseKnowledgeInfoFacade {

    @Autowired
    private BaseKnowledgeInfoService baseKnowledgeInfoService;
    @Autowired
    private BaseKnowledgeContentService baseKnowledgeContentService;

    @Override
    public boolean checkNameOnly(String name, Long id) {
        QueryWrapper<BaseKnowledgeInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        wrapper.ne("status", Constants.STATUS_DEL);
        if (null != id) {
            wrapper.ne("id", id);
        }
        return baseKnowledgeInfoService.count(wrapper) > 0;
    }

    @Override
    @Logging(code = "knowledgeInfo.add", vars = {"vo.name", "vo"}, type = EnumLogType.ADD)
    public boolean add(BaseKnowledgeInfoAddVo vo) {
        boolean b = checkNameOnly(vo.getName(), null);
        if (b) {
            throw new IatpException("名称已经存在");
        }
        BaseKnowledgeInfo info = new BaseKnowledgeInfo();
        BeanUtils.copyProperties(vo, info);
        info.setStatus(Constants.STATUS_NORMAL);
        info.setId(IdWorker.getId());
        return baseKnowledgeInfoService.add(info);
    }

    @Override
    @Logging(code = "knowledgeInfo.edit", vars = {"vo.name", "vo"}, type = EnumLogType.UPDATE)
    public boolean edit(BaseKnowledgeInfoEditVo vo) {
        boolean b = checkNameOnly(vo.getName(), vo.getId());
        if (b) {
            throw new IatpException("名称已经存在");
        }
        BaseKnowledgeInfo info = new BaseKnowledgeInfo();
        BeanUtils.copyProperties(vo, info);
        return baseKnowledgeInfoService.edit(info);
    }

    @Override
    public Page<BaseKnowledgeInfo> list(ParamPageVo<BaseKnowledgeInfoSearchVo> vo) {
        Page<BaseKnowledgeInfo> page = new Page<>(vo.getPage().getCurrent(), vo.getPage().getSize());
        return baseKnowledgeInfoService.selectPage(page, vo.getParams());
    }

    @Override
    public List<BaseKnowledgeInfo> listAll() {
        QueryWrapper<BaseKnowledgeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("status", Constants.STATUS_DEL);
        queryWrapper.ne("status", Constants.STATUS_DISABLED);
        return baseKnowledgeInfoService.list(queryWrapper);
    }

    @Override
    public BaseKnowledgeInfo selectById(Long id) {
        return baseKnowledgeInfoService.selectById(id);
    }

    @Override
    @Logging(code = "knowledgeInfo.del", vars = {"", "strings"}, type = EnumLogType.DEL)
    public boolean batchDel(List<String> strings) {
        QueryWrapper<BaseKnowledgeContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("father", strings);
        Integer count = baseKnowledgeContentService.count(queryWrapper);
        if (count > 0) {
            throw new IatpException("选中的知识库有被知识库内容引用不能删除");
        }
        return baseKnowledgeInfoService.batchDel(strings);
    }

    @Override
    public boolean uploadFile(Long id, String filename) {
        BaseKnowledgeInfo info = baseKnowledgeInfoService.selectById(id);
        info.setFilename(filename);
        return baseKnowledgeInfoService.edit(info);
    }

}
