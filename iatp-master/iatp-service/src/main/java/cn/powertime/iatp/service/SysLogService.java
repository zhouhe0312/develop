package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.SysLog;
import cn.powertime.iatp.vo.req.admin.SysLogListVo;
import cn.powertime.iatp.vo.resp.admin.SysLogExportVo;
import cn.powertime.iatp.vo.resp.admin.SysLogPageListVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 日志信息表 服务类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
public interface SysLogService extends IService<SysLog> {

    SysLog findById(Long id);

    IPage<SysLogPageListVo> selectPage(Page<SysLogPageListVo> page, SysLogListVo params);

    List<SysLogExportVo> export(SysLogListVo vo);

    boolean delete(List<Long> ids);
}
