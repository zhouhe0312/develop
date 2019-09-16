package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.BaseChapelTest;
import cn.powertime.iatp.vo.req.admin.BaseChapelTestSearchVo;
import cn.powertime.iatp.vo.req.web.CourseExamSeachVo;
import cn.powertime.iatp.vo.resp.admin.BaseChapelTestPageListVo;
import cn.powertime.iatp.vo.resp.admin.TopicListVo;
import cn.powertime.iatp.vo.resp.web.CourseExamPageListVo;
import cn.powertime.iatp.vo.resp.web.PaperDetailsVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 试卷表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseChapelTestService extends IService<BaseChapelTest> {

    boolean add(BaseChapelTest baseChapelTest);

    boolean edit(BaseChapelTest baseChapelTest);

    Page<BaseChapelTestPageListVo> selectPage(Page<BaseChapelTest> page, BaseChapelTestSearchVo params);

    BaseChapelTest selectById(Long id);

    boolean batchDel(List<String> strings);

    Page<CourseExamPageListVo> webCourseExamList(Page<BaseChapelTest> page, CourseExamSeachVo params, Long uid);

    PaperDetailsVo paperResult(Long id, Long uid);

    List<TopicListVo> getTopicListByChapelId(Long id);
}
