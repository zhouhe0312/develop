package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.entity.SysRes;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.SysMenuListVo;
import cn.powertime.iatp.vo.req.admin.SysResAddVo;
import cn.powertime.iatp.vo.req.admin.SysResEditVo;
import cn.powertime.iatp.vo.resp.admin.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
public interface SysResFacade {

    /**
     * 验证权限名称及编码唯一性
     *
     * @param type
     * @param value
     * @param id
     * @return
     */
    boolean checkNameAndCodeOnly(int type, String value, Long id);

    boolean add(SysResAddVo addVo);

    boolean edit(SysResEditVo editVo);

    boolean del(Long id);

    SysRes selectById(Long id);

    List<MenuTree> tree();

    List<ZtreeVo> ztree(Long roleId);

    MenuAndButtonVo menuTreeByUserId(Long userId);

    ElementTreeVo elementTree(Long roleId);

    //Map<String, ButtonVo> selectButtons(Long userId, Long menuId);

    Page<SysResPageVo> list(ParamPageVo<SysMenuListVo> vo);

    boolean batchDel(List<String> strings);
}
