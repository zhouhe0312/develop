package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.SysUser;
import cn.powertime.iatp.vo.req.admin.SysUserListVo;
import cn.powertime.iatp.vo.resp.admin.SysUserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    Page<SysUserVo> mySelectPage(Page page, @Param("vo")SysUserListVo params);

    Integer batchDel(@Param("ids")List<String> strings);
}
