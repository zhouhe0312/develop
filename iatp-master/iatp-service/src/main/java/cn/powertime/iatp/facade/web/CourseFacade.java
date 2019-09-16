package cn.powertime.iatp.facade.web;

import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.web.CoursePageListSearchVo;
import cn.powertime.iatp.vo.req.web.ResourceSearchVo;
import cn.powertime.iatp.vo.req.web.RecordVo;
import cn.powertime.iatp.vo.resp.web.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface CourseFacade {
    Page<IndexCourseList> selectPage(ParamPageVo<CoursePageListSearchVo> vo);

    boolean pvAdd(Long id);

    CourseDetails details(Long id);

    boolean record(RecordVo vo);

    List<ChapterListVo> chapterList(Long id,Long uid);

    CourseResourceRespListVo courseResourceList(ResourceSearchVo vo);

    List<ExperimentResourceRespListVo> experimentResourceList(ResourceSearchVo vo);
}
