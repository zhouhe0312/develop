package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.vo.req.admin.BuildPaperList;
import cn.powertime.iatp.vo.req.admin.SynthesizeBuildVo;

/**
 * <p>
 * 试卷试题表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseExaminationPaperFacade  {

    boolean build(BuildPaperList list);

    boolean synthesizeBuild(SynthesizeBuildVo vo);

    boolean isTestAssembly(Long testPaperId);
}
