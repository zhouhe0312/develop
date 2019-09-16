package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.entity.BaseStandard;
import cn.powertime.iatp.vo.req.admin.BaseStandardAddVo;
import cn.powertime.iatp.vo.req.admin.BaseStandardEditVo;

import java.util.List;

/**
 * <p>
 * 知识库内容标准表 服务类
 * </p>
 *
 * @author yang xin
 * @since 2019-07-04
 */
public interface BaseStandardFacade {

    boolean add(BaseStandardAddVo vo);

    boolean edit(BaseStandardEditVo vo);

    List<BaseStandard> list(Long controlId);

    boolean batchDel(List<String> strings);

}
