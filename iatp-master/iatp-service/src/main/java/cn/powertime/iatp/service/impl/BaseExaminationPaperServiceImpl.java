package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.BaseExaminationPaper;
import cn.powertime.iatp.mapper.BaseExaminationPaperMapper;
import cn.powertime.iatp.service.BaseExaminationPaperService;
import cn.powertime.iatp.vo.resp.web.TopicVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 试卷试题表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Service
public class BaseExaminationPaperServiceImpl extends ServiceImpl<BaseExaminationPaperMapper, BaseExaminationPaper> implements BaseExaminationPaperService {

    @Autowired
    private BaseExaminationPaperMapper baseExaminationPaperMapper;

    @Override
    public boolean build(List<BaseExaminationPaper> papers) {
        return saveBatch(papers);
    }

    @Override
    public List<TopicVo> getTopics(Long id) {
        return baseExaminationPaperMapper.getTopics(id);
    }

    @Override
    public List<TopicVo> getTopicsResult(Long id,Long resultId,Long uid) {
        return baseExaminationPaperMapper.getTopicsResult(id,resultId,uid);
    }

    @Override
    public List<Long> getTopicsByCouseId(Long courseId) {
        return baseExaminationPaperMapper.getTopicsByCouseId(courseId);
    }

    @Override
    public boolean delByTestIds(List<String> strings) {
         Integer i = baseExaminationPaperMapper.delByTestIds(strings) ;
        return i > 0;
    }
}
