package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.SysLog;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.mapper.SysLogMapper;
import cn.powertime.iatp.service.SysLogService;
import cn.powertime.iatp.vo.req.admin.SysLogListVo;
import cn.powertime.iatp.vo.resp.admin.SysLogExportVo;
import cn.powertime.iatp.vo.resp.admin.SysLogPageListVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 日志信息表 服务实现类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public SysLog findById(Long id) {
        return getById(id);
    }

    @Override
    public IPage<SysLogPageListVo> selectPage(Page<SysLogPageListVo> page, SysLogListVo params) {
        return sysLogMapper.mySelectPage(page,params);
    }

    @Override
    public List<SysLogExportVo> export(SysLogListVo vo) {
        return sysLogMapper.export(vo);
    }

    @Override
    public boolean delete(List<Long> ids) {
        Collection<SysLog> sysLogs = listByIds(ids);
        sysLogs.forEach(item ->{
            if(item.getBackups() == 1){
                throw new IatpException("选中数据包含不能删除数据！");
            }
        });
        return removeByIds(ids);
    }
}
