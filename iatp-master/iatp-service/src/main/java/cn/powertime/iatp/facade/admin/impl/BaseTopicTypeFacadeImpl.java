package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.BaseTopic;
import cn.powertime.iatp.entity.BaseTopicType;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.BaseTopicTypeFacade;
import cn.powertime.iatp.logging.EnumLogType;
import cn.powertime.iatp.logging.Logging;
import cn.powertime.iatp.service.BaseTopicService;
import cn.powertime.iatp.service.BaseTopicTypeService;
import cn.powertime.iatp.vo.req.admin.BaseTopicTypeAddVo;
import cn.powertime.iatp.vo.req.admin.BaseTopicTypeEditVo;
import cn.powertime.iatp.vo.req.admin.BaseTopicTypeSearchVo;
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

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 题库类型表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
@Order(2)
public class BaseTopicTypeFacadeImpl implements BaseTopicTypeFacade {

    @Autowired
    private BaseTopicTypeService baseTopicTypeService;
    @Autowired
    private BaseTopicService baseTopicService;

    @Override
    @Logging(code = "topicType.add",vars = {"vo.title","vo"},type = EnumLogType.ADD)
    public boolean add(BaseTopicTypeAddVo vo) {
        boolean b = checkNameOnly(vo.getTitle(),null);
        if(!b){
            throw new IatpException("题库名称已存在用");
        }
        BaseTopicType baseTopicType = new BaseTopicType();
        BeanUtils.copyProperties(vo,baseTopicType);
        baseTopicType.setCreateTime(LocalDateTime.now());
        baseTopicType.setUpdateTime(LocalDateTime.now());
        baseTopicType.setId(IdWorker.getId());
        return baseTopicTypeService.add(baseTopicType);
    }

    @Override
    @Logging(code = "topicType.edit",vars = {"vo.title","vo"},type = EnumLogType.UPDATE)
    public boolean edit(BaseTopicTypeEditVo vo) {
        boolean b = checkNameOnly(vo.getTitle(),vo.getId());
        if(!b){
            throw new IatpException("题库名称已存在用");
        }
        BaseTopicType baseTopicType = new BaseTopicType();
        BeanUtils.copyProperties(vo,baseTopicType);
        baseTopicType.setUpdateTime(LocalDateTime.now());
        return baseTopicTypeService.edit(baseTopicType);
    }

    @Override
    public Page<BaseTopicType> list(ParamPageVo<BaseTopicTypeSearchVo> vo) {
        Page<BaseTopicType> page = new Page<>(vo.getPage().getCurrent(),vo.getPage().getSize());
        return baseTopicTypeService.selectPage(page,vo.getParams());
    }

    @Override
    public BaseTopicType selectById(Long id) {
        return baseTopicTypeService.selectById(id);
    }

    @Override
    @Logging(code = "topicType.del",vars = {"","strings"},type = EnumLogType.DEL)
    public boolean batchDel(List<String> strings) {
        QueryWrapper<BaseTopic> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("type_id",strings);
        queryWrapper.ne("status", Constants.STATUS_DEL);
        int count = baseTopicService.count(queryWrapper);
        if(count > 0){
            throw new IatpException("题库类型被题库引用不能删除！");
        }
        return baseTopicTypeService.batchDel(strings);
    }

    @Override
    public List<BaseTopicType> listAll() {
        return baseTopicTypeService.listAll();
    }

    @Override
    public boolean checkNameOnly(String title, Long id) {
        QueryWrapper<BaseTopicType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",title);
        queryWrapper.ne("status",Constants.STATUS_DEL);
        if(id != null){
            queryWrapper.ne("id",id);
        }
        Integer i = baseTopicTypeService.count(queryWrapper);
        if(i == 0){
            return true;
        }
        return false;
    }
}
