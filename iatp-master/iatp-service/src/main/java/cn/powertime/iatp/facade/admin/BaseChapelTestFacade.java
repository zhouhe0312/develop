package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.entity.BaseChapelTest;
import cn.powertime.iatp.vo.req.admin.BaseChapelTestAddVo;
import cn.powertime.iatp.vo.req.admin.BaseChapelTestEditVo;
import cn.powertime.iatp.vo.req.admin.BaseChapelTestSearchVo;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.web.CourseExamSeachVo;
import cn.powertime.iatp.vo.req.web.SubmitPaperVo;
import cn.powertime.iatp.vo.req.web.WrongQuestionsSeachVo;
import cn.powertime.iatp.vo.resp.admin.BaseChapelTestPageListVo;
import cn.powertime.iatp.vo.resp.admin.KeyValueVo;
import cn.powertime.iatp.vo.resp.admin.TopicListVo;
import cn.powertime.iatp.vo.resp.web.CourseExamPageListVo;
import cn.powertime.iatp.vo.resp.web.PaperDetailsVo;
import cn.powertime.iatp.vo.resp.web.WrongQuestionsPageListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 试卷表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseChapelTestFacade {

    List<KeyValueVo> selectList();

    boolean add(BaseChapelTestAddVo vo);

    boolean edit(BaseChapelTestEditVo vo);

    Page<BaseChapelTestPageListVo> list(ParamPageVo<BaseChapelTestSearchVo> vo);

    List<BaseChapelTest> listAll();

    BaseChapelTest selectById(Long id);

    boolean batchDel(List<String> strings);

    boolean enableDisabled(String id);


    List<TopicListVo> getTopicListByChapelId(Long id);
}
