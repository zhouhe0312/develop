package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.SysDictType;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.SysDictTypeFacade;
import cn.powertime.iatp.service.SysDictTypeService;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.SysDictTypeListVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : liqi
 * Date: 2018-11-26
 * Time: 9:55
 */
@Facade
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class SysDictTypeFacadeImpl implements SysDictTypeFacade {

    @Autowired
    private SysDictTypeService sysDictTypeService;

    @Override
    public boolean checkNameOnly(String name, Long id) {
        QueryWrapper<SysDictType> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        wrapper.ne("status", Constants.STATUS_DEL);
        if (id != null) {
            wrapper.ne("id", id);
        }
        return sysDictTypeService.count(wrapper) > 0;
    }

    @Override
    public boolean add(String name) {
        boolean b = checkNameOnly(name, null);
        if (b) {
            throw new IatpException("名称已经存在");
        }
        SysDictType type = new SysDictType();
        type.setCreateTime(LocalDateTime.now());
        type.setId(IdWorker.getId());
        type.setName(name);
        type.setStatus(Constants.STATUS_NORMAL);
        type.setUpdateTime(LocalDateTime.now());
        return sysDictTypeService.ownSave(type);
    }

    @Override
    public boolean edit(String name, Long id) {
        boolean b = checkNameOnly(name, id);
        if (b) {
            throw new IatpException("名称已经存在");
        }
        SysDictType type = new SysDictType();
        type.setId(id);
        type.setName(name);
        type.setUpdateTime(LocalDateTime.now());
        return sysDictTypeService.ownUpdateById(type);
    }

    @Override
    public boolean del(Long id) {
        SysDictType type = new SysDictType();
        type.setId(id);
        type.setStatus(Constants.STATUS_DEL);
        return sysDictTypeService.del(type);
    }

    @Override
    public List<SysDictType> selectList() {
        QueryWrapper<SysDictType> wrapper = new QueryWrapper<>();
        wrapper.eq("status", Constants.STATUS_NORMAL);
        wrapper.orderByDesc("create_time");
        return sysDictTypeService.list(wrapper);
    }

    @Override
    public SysDictType selectById(Long id) {
        return sysDictTypeService.getById(id);
    }

    @Override
    public Page<SysDictType> list(ParamPageVo<SysDictTypeListVo> vo) {
        Page<SysDictType> page = new Page<>(vo.getPage().getCurrent(), vo.getPage().getSize());
        return sysDictTypeService.selectPage(page, vo.getParams().getName());

    }
}
