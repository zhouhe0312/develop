package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.SysRes;
import cn.powertime.iatp.vo.req.admin.SysMenuListVo;
import cn.powertime.iatp.vo.resp.admin.SysResAndChildCountVo;
import cn.powertime.iatp.vo.resp.admin.SysResPageVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
public interface SysResService extends IService<SysRes> {

    List<SysRes> selectZtree();

    List<SysRes> listByUserId(Long userId);

    boolean ownSave(SysRes menu);

    boolean ownUpdateById(SysRes menu);

    boolean del(SysRes menu);

    List<SysRes> buttonsByUserId(Long userId);

    Page<SysResPageVo> selectPage(Page<SysResPageVo> page, SysMenuListVo params);

    boolean batchDel(List<String> strings);

    List<SysResAndChildCountVo> listSysResAndChildCount();

    List<SysResAndChildCountVo> tree();
}
