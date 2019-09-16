package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.entity.BaseResource;
import cn.powertime.iatp.vo.req.admin.*;

import java.util.List;

/**
 * <p>
 * 课程（实验）资源表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseResourceFacade {

    /**
     * 验证名称是否重复
     *
     * @param chapterId
     * @param type
     * @param name
     * @param id
     * @return
     */
    boolean checkNameOnly(Long chapterId, int type, String name, Long id);

    boolean add(BaseResourceAddVo vo);

    boolean addTraining(BaseResourceTrainingAddVo vo);

    boolean edit(BaseResourceEditVo vo);

    boolean editTraining(BaseResourceTrainingEditVo vo);

    List<BaseResource> listAll(Long chapterPid, Long chapterId);

    boolean batchDel(List<String> strings);

    boolean sort(BaseResourceSortVo vo);

}
