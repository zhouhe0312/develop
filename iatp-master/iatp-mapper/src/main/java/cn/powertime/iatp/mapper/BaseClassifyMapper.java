package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.BaseClassify;
import cn.powertime.iatp.vo.req.admin.BaseClassifySearchVo;
import cn.powertime.iatp.vo.resp.admin.BaseClassifyPageListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Mapper
public interface BaseClassifyMapper extends BaseMapper<BaseClassify> {

    Page<BaseClassifyPageListVo> mySelectPage(Page page, @Param("vo") BaseClassifySearchVo params);

    Integer batchDel(@Param("ids") List<String> strings);

}
