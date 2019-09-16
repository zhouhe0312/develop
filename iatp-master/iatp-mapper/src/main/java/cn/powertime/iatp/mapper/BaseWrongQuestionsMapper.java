package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.BaseWrongQuestions;
import cn.powertime.iatp.vo.req.admin.WrongQuestionsReqListVo;
import cn.powertime.iatp.vo.req.web.WrongQuestionsSeachVo;
import cn.powertime.iatp.vo.resp.admin.WrongQuestionsRespListVo;
import cn.powertime.iatp.vo.resp.web.WrongQuestionsPageListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 错题表 Mapper 接口
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Mapper
public interface BaseWrongQuestionsMapper extends BaseMapper<BaseWrongQuestions> {

    Page<WrongQuestionsRespListVo> selectPage(Page page, @Param("vo") WrongQuestionsReqListVo vo);

    Page<WrongQuestionsPageListVo> wrongList(Page page, @Param("vo") WrongQuestionsSeachVo params, @Param("uid") Long uid);
}
