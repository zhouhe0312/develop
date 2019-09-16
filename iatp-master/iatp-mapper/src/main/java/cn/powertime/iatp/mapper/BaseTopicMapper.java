package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.BaseTopic;
import cn.powertime.iatp.vo.req.admin.BaseTopicSearchVo;
import cn.powertime.iatp.vo.resp.admin.BaseTopicPageListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 题库表 Mapper 接口
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Mapper
public interface BaseTopicMapper extends BaseMapper<BaseTopic> {

    Page<BaseTopicPageListVo> mySelectPage(Page page, @Param("vo") BaseTopicSearchVo params);

    Integer batchDel(@Param("ids")List<String> strings);
}
