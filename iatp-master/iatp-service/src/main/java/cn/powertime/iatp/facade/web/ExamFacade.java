package cn.powertime.iatp.facade.web;

import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.web.CourseExamSeachVo;
import cn.powertime.iatp.vo.req.web.SubmitPaperVo;
import cn.powertime.iatp.vo.req.web.WrongQuestionsSeachVo;
import cn.powertime.iatp.vo.resp.web.CourseExamPageListVo;
import cn.powertime.iatp.vo.resp.web.PaperDetailsVo;
import cn.powertime.iatp.vo.resp.web.SubmitPaperRespVo;
import cn.powertime.iatp.vo.resp.web.WrongQuestionsPageListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface ExamFacade {

    Page<CourseExamPageListVo> webCourseExamList(ParamPageVo<CourseExamSeachVo> vo);

    boolean collect(Long id, Integer type);

    PaperDetailsVo paperDetail(Long id);

    PaperDetailsVo paperResultDetail(Long id,Long resultId);

    SubmitPaperRespVo submitPaper(SubmitPaperVo vo);

    Page<WrongQuestionsPageListVo> wrongList(ParamPageVo<WrongQuestionsSeachVo> vo);
}
