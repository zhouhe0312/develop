package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.SysLog;
import cn.powertime.iatp.vo.req.admin.SysLogListVo;
import cn.powertime.iatp.vo.resp.admin.SysLogExportVo;
import cn.powertime.iatp.vo.resp.admin.SysLogPageListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 日志信息表 Mapper 接口
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

    IPage<SysLogPageListVo> mySelectPage(Page page, @Param("vo") SysLogListVo params);

    List<SysLogExportVo> export(@Param("vo")SysLogListVo vo);
}
