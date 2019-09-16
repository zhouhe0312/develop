package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.entity.SysRole;
import cn.powertime.iatp.vo.req.admin.*;
import cn.powertime.iatp.vo.resp.admin.SysRolePageListVo;
import cn.powertime.iatp.vo.resp.admin.UserRoleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
public interface SysRoleFacade {

    /**
     * 验证角色名称及编码唯一性
     *
     * @param type
     * @param value
     * @param id
     * @return
     */
    boolean checkNameAndCodeOnly(int type, String value, Long id);

    boolean add(SysRoleAddVo addVo);

    boolean edit(SysRoleEditVo editVo);

    boolean del(Long id);

    IPage<SysRolePageListVo> list(ParamPageVo<SysRoleListVo> vo);

    List<SysRole> listAll();

    SysRole selectById(Long id);

    boolean auth(SysRoleAuthVo authVo);

    boolean batchDel(List<String> strings);

    List<UserRoleVo> userRole(Long uid);
}
