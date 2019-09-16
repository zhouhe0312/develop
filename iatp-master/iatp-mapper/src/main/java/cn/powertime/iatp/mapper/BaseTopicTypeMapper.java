package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.BaseTopicType;
import cn.powertime.iatp.vo.req.admin.BaseTopicTypeSearchVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 题库类型表 Mapper 接口
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Mapper
public interface BaseTopicTypeMapper extends BaseMapper<BaseTopicType> {

    Page<BaseTopicType> mySelectPage(Page page, @Param("vo") BaseTopicTypeSearchVo params);

    Integer batchDel(@Param("ids")List<String> strings);
}
