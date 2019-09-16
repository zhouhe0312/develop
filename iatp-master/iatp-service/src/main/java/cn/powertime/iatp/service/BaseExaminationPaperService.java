package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.BaseExaminationPaper;
import cn.powertime.iatp.vo.resp.web.TopicVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 试卷试题表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseExaminationPaperService extends IService<BaseExaminationPaper> {

    boolean build(List<BaseExaminationPaper> papers);

    List<TopicVo> getTopics(Long id);

    List<TopicVo> getTopicsResult(Long id,Long resultId,Long uid);

    List<Long> getTopicsByCouseId(Long courseOneId);

    boolean delByTestIds(List<String> strings);
}
