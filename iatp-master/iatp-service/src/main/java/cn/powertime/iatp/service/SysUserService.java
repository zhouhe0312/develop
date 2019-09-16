package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.SysUser;
import cn.powertime.iatp.vo.req.admin.SysUserListVo;
import cn.powertime.iatp.vo.resp.admin.SysUserVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
public interface SysUserService extends IService<SysUser> {

    boolean ownSave(SysUser user);

    boolean edit(SysUser user);

    boolean del(SysUser user);

    SysUser findById(Long id);

    Page<SysUserVo> selectPage(Page<SysUserVo> page, SysUserListVo params);

    boolean batchDel(List<String> strings);
}
