package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.WrongQuestionsReqListVo;
import cn.powertime.iatp.vo.resp.admin.WrongQuestionsRespListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 错题表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseWrongQuestionsFacade {
    /**
     * 用户错题列表
     * @param vo 错题筛选条件
     * @return 返回符合条件的错题
     */
    Page<WrongQuestionsRespListVo> list(ParamPageVo<WrongQuestionsReqListVo> vo);

    /**
     * 删除用户错题信息
     * @param ids 错题Id列表
     * @return 返回是否删除成功
     */
    boolean delete(List<Long> ids);
}
