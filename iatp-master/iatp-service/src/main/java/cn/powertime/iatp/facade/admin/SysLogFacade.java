package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.entity.SysLog;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.SysLogListVo;
import cn.powertime.iatp.vo.resp.admin.SysLogExportVo;
import cn.powertime.iatp.vo.resp.admin.SysLogPageListVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 日志信息表 服务类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
public interface SysLogFacade {

    IPage<SysLogPageListVo> list(ParamPageVo<SysLogListVo> vo);

    SysLog findById(@NotNull(message = "日志ID不能为空") Long id);

    List<SysLogExportVo> export(SysLogListVo vo);

    boolean delete(List<Long> ids);

    /**
     * 查询指定天数前的所有数据
     *
     * @return
     */
    List<SysLog> selectListByDay();
}
