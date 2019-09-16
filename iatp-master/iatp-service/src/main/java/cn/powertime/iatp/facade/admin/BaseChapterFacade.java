package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.vo.req.admin.BaseChapterAddVo;
import cn.powertime.iatp.vo.req.admin.BaseChapterEditVo;
import cn.powertime.iatp.vo.resp.admin.CommonTree;
import cn.powertime.iatp.vo.resp.admin.KeyValueVo;

import java.util.List;

/**
 * <p>
 * 课程（实验）章节表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseChapterFacade {

    /**
     * 验证名称是否存在
     *
     * @param courseId
     * @param pid
     * @param type
     * @param name
     * @param id
     * @return
     */
    boolean checkNameOnly(Long courseId, Long pid, int type, String name, Long id);

    boolean add(BaseChapterAddVo vo);

    boolean edit(BaseChapterEditVo vo);

    List<KeyValueVo> selectList(Integer type, Long pidOrCourseId);

    boolean batchDel(List<String> strings);

    List<CommonTree> tree(Integer type, Long courseId);

}
