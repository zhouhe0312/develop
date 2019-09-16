package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.SysRes;
import cn.powertime.iatp.vo.req.admin.SysMenuListVo;
import cn.powertime.iatp.vo.resp.admin.SysResAndChildCountVo;
import cn.powertime.iatp.vo.resp.admin.SysResPageVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Mapper
public interface SysResMapper extends BaseMapper<SysRes> {

    List<SysRes> selectZtree();

    List<SysRes> listByUserId(Long userId);

    List<SysRes> buttonsByUserId(@Param("userId") Long userId);

    Page<SysResPageVo> mySelectPage(Page page, @Param("vo") SysMenuListVo params);

    Integer batchDel(@Param("ids")List<String> strings);

    List<SysResAndChildCountVo> listSysResAndChildCount();

    List<SysResAndChildCountVo> tree();
}
