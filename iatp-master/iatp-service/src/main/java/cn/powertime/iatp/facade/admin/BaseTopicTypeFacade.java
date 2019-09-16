package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.entity.BaseTopicType;
import cn.powertime.iatp.vo.req.admin.BaseTopicTypeAddVo;
import cn.powertime.iatp.vo.req.admin.BaseTopicTypeEditVo;
import cn.powertime.iatp.vo.req.admin.BaseTopicTypeSearchVo;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 题库类型表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseTopicTypeFacade {

    boolean add(BaseTopicTypeAddVo vo);

    boolean edit(BaseTopicTypeEditVo vo);

    Page<BaseTopicType> list(ParamPageVo<BaseTopicTypeSearchVo> vo);

    BaseTopicType selectById(Long id);

    boolean batchDel(List<String> strings);

    List<BaseTopicType> listAll();

    boolean checkNameOnly(String title, Long id);
}
