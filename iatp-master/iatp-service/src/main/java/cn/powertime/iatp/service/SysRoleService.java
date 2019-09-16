package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.SysRole;
import cn.powertime.iatp.vo.resp.admin.SysRolePageListVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
public interface SysRoleService extends IService<SysRole> {

    IPage<SysRolePageListVo> selectPage(Page<SysRole> page, String name, String code);

    boolean ownSave(SysRole role);

    boolean ownUpdateById(SysRole role);

    boolean del(SysRole sysRole);

    boolean batchDel(List<String> strings);
}
