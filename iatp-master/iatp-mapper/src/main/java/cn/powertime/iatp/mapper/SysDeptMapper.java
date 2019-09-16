package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.SysDept;
import cn.powertime.iatp.vo.req.admin.SysDeptListVo;
import cn.powertime.iatp.vo.resp.admin.SysDeptPageListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author liqi
 * @since 2019-05-20
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    IPage<SysDeptPageListVo> mySelectPage(Page page, @Param("vo") SysDeptListVo params);

    Integer batchDel( @Param("ids")List<String> strings);
}
