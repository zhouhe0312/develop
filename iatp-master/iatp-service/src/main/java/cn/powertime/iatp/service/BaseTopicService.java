package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.BaseTopic;
import cn.powertime.iatp.vo.req.admin.BaseTopicImportVo;
import cn.powertime.iatp.vo.req.admin.BaseTopicSearchVo;
import cn.powertime.iatp.vo.resp.admin.BaseTopicPageListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 题库表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseTopicService extends IService<BaseTopic> {

    boolean add(BaseTopic topic);

    boolean edit(BaseTopic topic);

    Page<BaseTopicPageListVo> selectPage(Page<BaseTopicPageListVo> page, BaseTopicSearchVo params);

    BaseTopic selectById(Long id);

    boolean batchDel(List<String> strings);

    boolean  importExcel(List<BaseTopicImportVo> result,Long typeId);
}
