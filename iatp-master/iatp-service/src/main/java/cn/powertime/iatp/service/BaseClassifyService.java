package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.BaseClassify;
import cn.powertime.iatp.vo.req.admin.BaseClassifySearchVo;
import cn.powertime.iatp.vo.resp.admin.BaseClassifyPageListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseClassifyService extends IService<BaseClassify> {

    boolean add(BaseClassify classify);

    boolean edit(BaseClassify classify);

    Page<BaseClassifyPageListVo> selectPage(Page<BaseClassifyPageListVo> page, BaseClassifySearchVo params);

    BaseClassify selectById(Long id);

    boolean batchDel(List<String> strings);

}
