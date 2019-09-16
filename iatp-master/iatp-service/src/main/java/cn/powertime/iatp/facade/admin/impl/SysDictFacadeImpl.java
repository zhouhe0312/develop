package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.SysDict;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.SysDictFacade;
import cn.powertime.iatp.logging.EnumLogType;
import cn.powertime.iatp.logging.Logging;
import cn.powertime.iatp.service.SysDictService;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.SysDictAddVo;
import cn.powertime.iatp.vo.req.admin.SysDictEditVo;
import cn.powertime.iatp.vo.req.admin.SysDictListVo;
import cn.powertime.iatp.vo.resp.admin.SysDictVo;
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

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ZYW
 * @version 1.0
 * @date 2019-04-16 18:53
 */
@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
@Order(2)
public class SysDictFacadeImpl implements SysDictFacade {

    @Autowired
    private SysDictService sysDictService;

    @Override
    public boolean checkNameAndValueOnly(int type, String value, Long id, Long typeId) {
        QueryWrapper<SysDict> wrapper = new QueryWrapper<>();
        wrapper.eq("type_id", typeId);
        if (type != 1 && type != 2) {
            throw new IatpException("类型输入有误，只能为1或2");
        }
        //1：名称 2：编码
        if (type == 1) {
            wrapper.eq("name", value);
        }
        if (type == 2) {
            wrapper.eq("value", value);
        }
        wrapper.ne("status", Constants.STATUS_DEL);
        if (id != null) {
            wrapper.ne("id", id);
        }
        return sysDictService.count(wrapper) > 0;
    }

    @Override
    public List<SysDict> selectByTypeId(Long typeId) {
        QueryWrapper<SysDict> sysDictQueryWrapper = new QueryWrapper<>();
        sysDictQueryWrapper.eq("type_id", typeId);
        sysDictQueryWrapper.eq("status", Constants.STATUS_NORMAL);
        sysDictQueryWrapper.orderByDesc("create_time");
        return sysDictService.list(sysDictQueryWrapper);
    }

    @Override
    public SysDict selectById(@NotNull(message = "字典类型ID不能为空") Long id) {
        return sysDictService.getById(id);
    }

    @Override
    @Logging(code = "dict.add", vars = {"dictVo.name", "dictVo"}, type = EnumLogType.ADD)
    public boolean add(SysDictAddVo dictVo) {
        boolean name = checkNameAndValueOnly(1, dictVo.getName(), null, dictVo.getTypeId());
        if (name) {
            throw new IatpException("字典名称已经存在");
        }
        boolean code = checkNameAndValueOnly(2, dictVo.getValue(), null, dictVo.getTypeId());
        if (code) {
            throw new IatpException("字典值已经存在");
        }
        SysDict dict = new SysDict();
        BeanUtils.copyProperties(dictVo, dict);
        dict.setId(IdWorker.getId());
        dict.setCreateTime(LocalDateTime.now());
        dict.setUpdateTime(LocalDateTime.now());
        dict.setStatus(Constants.STATUS_NORMAL);
        return sysDictService.ownSave(dict);
    }

    @Override
    @Logging(code = "dict.edit", vars = {"vo.name", "vo"}, type = EnumLogType.UPDATE)
    public boolean edit(SysDictEditVo vo) {
        boolean name = checkNameAndValueOnly(1, vo.getName(), vo.getId(), vo.getTypeId());
        if (name) {
            throw new IatpException("字典名称已经存在");
        }
        boolean code = checkNameAndValueOnly(2, vo.getValue(), vo.getId(), vo.getTypeId());
        if (code) {
            throw new IatpException("字典值已经存在");
        }
        SysDict dict = new SysDict();
        BeanUtils.copyProperties(vo, dict);
        dict.setUpdateTime(LocalDateTime.now());
        return sysDictService.ownUpdateById(dict);
    }

    @Override
    public boolean del(Long id) {
        SysDict dict = new SysDict();
        dict.setId(id);
        dict.setStatus(Constants.STATUS_DEL);
        return sysDictService.del(dict);
    }

    @Override
    public Page<SysDictVo> list(ParamPageVo<SysDictListVo> vo) {
        Page<SysDictVo> page = new Page<>(vo.getPage().getCurrent(), vo.getPage().getSize());
        return sysDictService.selectPage(page, vo.getParams());
    }

    @Override
    @Logging(code = "dict.del", vars = {"", "strings"}, type = EnumLogType.DEL)
    public boolean batchDel(List<String> strings) {
        return sysDictService.batchDel(strings);
    }

}
