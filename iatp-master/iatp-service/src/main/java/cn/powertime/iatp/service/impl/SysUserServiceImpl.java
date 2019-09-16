package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.SysUser;
import cn.powertime.iatp.mapper.SysUserMapper;
import cn.powertime.iatp.service.SysUserService;
import cn.powertime.iatp.vo.req.admin.SysUserListVo;
import cn.powertime.iatp.vo.resp.admin.SysUserVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public boolean ownSave(SysUser user) {
        return save(user);
    }

    @Override
    public boolean edit(SysUser user) {
        return updateById(user);
    }

    @Override
    public boolean del(SysUser user) {
        return updateById(user);
    }

    @Override
    public SysUser findById(Long id) {
        return getById(id);
    }

    @Override
    public Page<SysUserVo> selectPage(Page<SysUserVo> page, SysUserListVo params) {
        return sysUserMapper.mySelectPage(page,params);
    }

    @Override
    public boolean batchDel(List<String> strings) {
        Integer i = sysUserMapper.batchDel(strings);
        return i > 0;
    }
}
