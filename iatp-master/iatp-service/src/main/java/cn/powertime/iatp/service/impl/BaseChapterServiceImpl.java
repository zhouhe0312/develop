package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.BaseChapter;
import cn.powertime.iatp.mapper.BaseChapterMapper;
import cn.powertime.iatp.service.BaseChapterService;
import cn.powertime.iatp.vo.resp.web.ChapterListVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程（实验）章节表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Service
public class BaseChapterServiceImpl extends ServiceImpl<BaseChapterMapper, BaseChapter> implements BaseChapterService {

    @Autowired
    private BaseChapterMapper baseChapterMapper;

    @Override
    public boolean add(BaseChapter baseChapter) {
        return save(baseChapter);
    }

    @Override
    public boolean edit(BaseChapter baseChapter) {
        return updateById(baseChapter);
    }

    @Override
    public boolean batchDel(List<String> strings) {
        Integer i = baseChapterMapper.batchDel(strings);
        return i > 0;
    }

    @Override
    public List<ChapterListVo> chapterList(Long id, Long uid) {
        return baseChapterMapper.chapterList(id,uid);
    }

}
