package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.SysRole;
import cn.powertime.iatp.vo.resp.admin.SysRolePageListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    IPage<SysRolePageListVo> mySelectPage(Page page, @Param("name")String name, @Param("code")String code);


    Integer batchDel(@Param("ids")List<String> strings);
}
