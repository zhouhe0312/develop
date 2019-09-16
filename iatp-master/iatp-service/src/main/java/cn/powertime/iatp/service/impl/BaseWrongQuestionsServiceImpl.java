package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.BaseChapelTest;
import cn.powertime.iatp.entity.BaseWrongQuestions;
import cn.powertime.iatp.mapper.BaseWrongQuestionsMapper;
import cn.powertime.iatp.service.BaseWrongQuestionsService;
import cn.powertime.iatp.vo.req.admin.WrongQuestionsReqListVo;
import cn.powertime.iatp.vo.req.web.WrongQuestionsSeachVo;
import cn.powertime.iatp.vo.resp.admin.WrongQuestionsRespListVo;
import cn.powertime.iatp.vo.resp.web.WrongQuestionsPageListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 错题表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Service
public class BaseWrongQuestionsServiceImpl extends ServiceImpl<BaseWrongQuestionsMapper, BaseWrongQuestions> implements BaseWrongQuestionsService {

    @Override
    public Page<WrongQuestionsRespListVo> selectPage(Page<WrongQuestionsRespListVo> page, WrongQuestionsReqListVo params) {
        return this.baseMapper.selectPage(page,params);
    }

    @Override
    public void batchAdd(List<BaseWrongQuestions> baseWrongQuestionsList) {
        saveBatch(baseWrongQuestionsList);
    }

    @Override
    public Page<WrongQuestionsPageListVo> wrongList(Page<BaseChapelTest> page, WrongQuestionsSeachVo params, Long uid) {
        return this.baseMapper.wrongList(page,params,uid);
    }

    @Override
    public void batchUpdate(List<BaseWrongQuestions> updateBaseWrongQuestionsList) {
        updateBatchById(updateBaseWrongQuestionsList);
    }
}
