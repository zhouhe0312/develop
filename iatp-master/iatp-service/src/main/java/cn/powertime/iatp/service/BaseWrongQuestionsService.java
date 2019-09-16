package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.BaseChapelTest;
import cn.powertime.iatp.entity.BaseWrongQuestions;
import cn.powertime.iatp.vo.req.admin.WrongQuestionsReqListVo;
import cn.powertime.iatp.vo.req.web.WrongQuestionsSeachVo;
import cn.powertime.iatp.vo.resp.admin.WrongQuestionsRespListVo;
import cn.powertime.iatp.vo.resp.web.WrongQuestionsPageListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 错题表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseWrongQuestionsService extends IService<BaseWrongQuestions> {
    /**
     * 查询错误列表
     * @param page 分页信息
     * @param params 筛选条件
     * @return 返回符合条件的数据
     */
    Page<WrongQuestionsRespListVo> selectPage(Page<WrongQuestionsRespListVo> page, WrongQuestionsReqListVo params);

    void batchAdd(List<BaseWrongQuestions> baseWrongQuestionsList);

    Page<WrongQuestionsPageListVo> wrongList(Page<BaseChapelTest> page, WrongQuestionsSeachVo params, Long currentUserId);

    void batchUpdate(List<BaseWrongQuestions> updateBaseWrongQuestionsList);
}
