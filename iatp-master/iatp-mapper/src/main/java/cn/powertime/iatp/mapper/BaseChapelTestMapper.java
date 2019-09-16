package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.BaseChapelTest;
import cn.powertime.iatp.vo.req.admin.BaseChapelTestSearchVo;
import cn.powertime.iatp.vo.req.web.CourseExamSeachVo;
import cn.powertime.iatp.vo.resp.admin.BaseChapelTestPageListVo;
import cn.powertime.iatp.vo.resp.admin.TopicListVo;
import cn.powertime.iatp.vo.resp.web.CourseExamPageListVo;
import cn.powertime.iatp.vo.resp.web.PaperDetailsVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 试卷表 Mapper 接口
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Mapper
public interface BaseChapelTestMapper extends BaseMapper<BaseChapelTest> {

    Page<BaseChapelTestPageListVo> mySelectPage(Page page, @Param("vo") BaseChapelTestSearchVo params);

    Integer batchDel(@Param("ids")List<String> strings);

    Page<CourseExamPageListVo> webCourseExamList(Page page, @Param("vo") CourseExamSeachVo params, @Param("uid") Long uid);

    PaperDetailsVo paperResult(@Param("id")Long id, @Param("uid") Long uid);

    List<TopicListVo> getTopicListByChapelId( @Param("id") Long id);
}
