package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.SysDictType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 字典类型表 Mapper 接口
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Mapper
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {

    Page<SysDictType> mySelectPage(Page page, @Param("name")String name);
}
