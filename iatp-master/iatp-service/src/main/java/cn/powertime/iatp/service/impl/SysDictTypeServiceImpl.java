package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.SysDictType;
import cn.powertime.iatp.mapper.SysDictTypeMapper;
import cn.powertime.iatp.service.SysDictTypeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;
    @Override
    public Page<SysDictType> selectPage(Page<SysDictType> page, String name) {
        return sysDictTypeMapper.mySelectPage(page,name);
    }

    @Override
    //@Logging(code = "type.add",vars = {"type.name","type"},type = EnumLogType.ADD)
    public boolean ownSave(SysDictType type) {
        return save(type);
    }

    @Override
    //@Logging(code = "type.update",vars = {"type.name","type"},type = EnumLogType.UPDATE)
    public boolean ownUpdateById(SysDictType type) {
        return updateById(type);
    }

    @Override
    //@Logging(code = "type.del",vars = {"type.id","type"},type = EnumLogType.DEL)
    public boolean del(SysDictType type) {
        return updateById(type);
    }

}
