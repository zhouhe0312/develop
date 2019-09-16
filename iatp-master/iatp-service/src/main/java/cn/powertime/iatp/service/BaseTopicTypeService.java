package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.BaseTopicType;
import cn.powertime.iatp.vo.req.admin.BaseTopicTypeSearchVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 题库类型表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseTopicTypeService extends IService<BaseTopicType> {

    boolean add(BaseTopicType baseTopicType);

    boolean edit(BaseTopicType baseTopicType);

    Page<BaseTopicType> selectPage(Page<BaseTopicType> page, BaseTopicTypeSearchVo params);

    BaseTopicType selectById(Long id);

    boolean batchDel(List<String> strings);

    List<BaseTopicType> listAll();
}
