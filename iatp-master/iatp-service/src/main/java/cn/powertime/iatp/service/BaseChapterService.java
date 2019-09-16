package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.BaseChapter;
import cn.powertime.iatp.vo.resp.web.ChapterListVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程（实验）章节表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseChapterService extends IService<BaseChapter> {

    boolean add(BaseChapter baseChapter);

    boolean edit(BaseChapter baseChapter);

    boolean batchDel(List<String> strings);

    List<ChapterListVo> chapterList(Long id,Long uid);
}
