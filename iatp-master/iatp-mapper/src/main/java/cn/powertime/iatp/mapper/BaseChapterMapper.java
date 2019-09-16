package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.BaseChapter;
import cn.powertime.iatp.vo.resp.web.ChapterListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程（实验）章节表 Mapper 接口
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Mapper
public interface BaseChapterMapper extends BaseMapper<BaseChapter> {

    Integer batchDel(@Param("ids") List<String> strings);

    List<ChapterListVo> chapterList(@Param("id") Long id, @Param("uid") Long uid);
}
