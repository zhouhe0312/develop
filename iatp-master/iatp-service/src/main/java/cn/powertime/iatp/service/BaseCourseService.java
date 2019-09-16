package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.BaseChapelTest;
import cn.powertime.iatp.entity.BaseCourse;
import cn.powertime.iatp.vo.req.admin.BaseCourseImportVo;
import cn.powertime.iatp.vo.req.admin.BaseCourseSearchVo;
import cn.powertime.iatp.vo.req.admin.BaseCourseUploadVo;
import cn.powertime.iatp.vo.req.web.CoursePageListSearchVo;
import cn.powertime.iatp.vo.resp.web.CourseDetails;
import cn.powertime.iatp.vo.resp.web.IndexCourseList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程（实验）表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseCourseService extends IService<BaseCourse> {

    boolean add(BaseCourse course);

    boolean edit(BaseCourse course);

    Page<BaseCourse> selectPage(Page<BaseCourse> page, BaseCourseSearchVo params);

    BaseCourse selectById(Long id);

    boolean batchDel(List<String> strings);

    boolean importExcel(List<BaseCourseImportVo> result, BaseCourseUploadVo vo);

    List<IndexCourseList> webIndexCourseList(Integer type);

    Page<IndexCourseList> webSelectPage(Page<IndexCourseList> page, CoursePageListSearchVo params);

    CourseDetails webDetails(Long id);
}
