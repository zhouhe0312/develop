package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.BaseCourse;
import cn.powertime.iatp.vo.req.admin.BaseCourseSearchVo;
import cn.powertime.iatp.vo.req.web.CoursePageListSearchVo;
import cn.powertime.iatp.vo.resp.web.CourseDetails;
import cn.powertime.iatp.vo.resp.web.IndexCourseList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程（实验）表 Mapper 接口
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Mapper
public interface BaseCourseMapper extends BaseMapper<BaseCourse> {

    Integer batchDel(@Param("ids") List<String> strings);

    List<IndexCourseList> webIndexCourseList(@Param("type") Integer type);

    Page<IndexCourseList> webSelectPage(Page page, @Param("vo")CoursePageListSearchVo params);

    CourseDetails webDetails(@Param("id") Long id);

    Page<BaseCourse> mySelectPage(Page page, BaseCourseSearchVo vo);
}
