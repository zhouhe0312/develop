package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.BaseExaminationPaper;
import cn.powertime.iatp.vo.resp.web.TopicVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 试卷试题表 Mapper 接口
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Mapper
public interface BaseExaminationPaperMapper extends BaseMapper<BaseExaminationPaper> {

    List<TopicVo> getTopics(@Param("id") Long id);

    List<TopicVo> getTopicsResult(@Param("id") Long id,@Param("resultId") Long resultId,@Param("uid")Long uid);

    List<Long> getTopicsByCouseId(@Param("courseId")Long courseId);

    Integer delByTestIds(@Param("testIds") List<String> strings);
}
