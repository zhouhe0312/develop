package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.SysDict;
import cn.powertime.iatp.vo.req.admin.SysDictListVo;
import cn.powertime.iatp.vo.resp.admin.SysDictVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {

    Page<SysDictVo> mySelectPage(Page page, @Param("vo") SysDictListVo params);

    Integer batchDel( @Param("ids")List<String> strings);
}
