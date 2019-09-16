package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.BaseKnowledgeContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 知识内容表 Mapper 接口
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Mapper
public interface BaseKnowledgeContentMapper extends BaseMapper<BaseKnowledgeContent> {

    Integer batchDel(@Param("ids") List<String> strings);

}
