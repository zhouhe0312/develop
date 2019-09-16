package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.SysRes;
import cn.powertime.iatp.mapper.SysResMapper;
import cn.powertime.iatp.service.SysResService;
import cn.powertime.iatp.vo.req.admin.SysMenuListVo;
import cn.powertime.iatp.vo.resp.admin.SysResAndChildCountVo;
import cn.powertime.iatp.vo.resp.admin.SysResPageVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Service
public class SysResServiceImpl extends ServiceImpl<SysResMapper, SysRes> implements SysResService {

    @Autowired
    private SysResMapper sysResMapper;
    @Override
    public List<SysRes> selectZtree() {
        return sysResMapper.selectZtree();
    }

    @Override
    public List<SysRes> listByUserId(Long userId) {
        return sysResMapper.listByUserId(userId);
    }

    @Override
    //@Logging(code = "menu.add",vars = {"menu.name","menu"},type = EnumLogType.ADD)
    public boolean ownSave(SysRes menu) {
        return save(menu);
    }

    @Override
    //@Logging(code = "menu.update",vars = {"menu.name","menu"},type = EnumLogType.UPDATE)
    public boolean ownUpdateById(SysRes menu) {
        return updateById(menu);
    }

    @Override
    //@Logging(code = "menu.del",vars = {"menu.id","menu"},type = EnumLogType.DEL)
    public boolean del(SysRes menu) {
        return updateById(menu);
    }

    @Override
    public List<SysRes> buttonsByUserId(Long userId) {
        return sysResMapper.buttonsByUserId(userId);
    }

    @Override
    public Page<SysResPageVo> selectPage(Page<SysResPageVo> page, SysMenuListVo params) {
        return sysResMapper.mySelectPage(page,params);
    }

    @Override
    public boolean batchDel(List<String> strings) {
        Integer i = sysResMapper.batchDel(strings);
        return i > 0;
    }

    @Override
    public List<SysResAndChildCountVo> listSysResAndChildCount() {
        return sysResMapper.listSysResAndChildCount();
    }

    @Override
    public List<SysResAndChildCountVo> tree() {
        return sysResMapper.tree();
    }


}
