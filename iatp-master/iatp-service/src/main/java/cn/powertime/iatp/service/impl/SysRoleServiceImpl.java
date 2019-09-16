package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.SysRole;
import cn.powertime.iatp.mapper.SysRoleMapper;
import cn.powertime.iatp.service.SysRoleService;
import cn.powertime.iatp.vo.resp.admin.SysRolePageListVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public IPage<SysRolePageListVo> selectPage(Page<SysRole> page, String name, String code ) {
        return sysRoleMapper.mySelectPage(page,name,code);
    }

    @Override
    //@Logging(code = "role.add",vars = {"role.name","role"},type = EnumLogType.ADD)
    public boolean ownSave(SysRole role) {
        return save(role);
    }

    @Override
    //@Logging(code = "role.update",vars = {"role.name","role"},type = EnumLogType.UPDATE)
    public boolean ownUpdateById(SysRole role) {
        return updateById(role);
    }

    @Override
    //@Logging(code = "role.del",vars = {"role.id","role"},type = EnumLogType.DEL)
    public boolean del(SysRole role) {
        return updateById(role);
    }

    @Override
    public boolean batchDel(List<String> strings) {
        Integer i = sysRoleMapper.batchDel(strings);
        return i > 0;
    }
}
