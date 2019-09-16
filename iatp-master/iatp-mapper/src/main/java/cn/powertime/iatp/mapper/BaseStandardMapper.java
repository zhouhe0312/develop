package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.BaseStandard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 知识库内容标准表 Mapper 接口
 * </p>
 *
 * @author yang xin
 * @since 2019-07-04
 */
@Mapper
public interface BaseStandardMapper extends BaseMapper<BaseStandard> {

    Integer batchDel(@Param("ids") List<String> strings);

}
