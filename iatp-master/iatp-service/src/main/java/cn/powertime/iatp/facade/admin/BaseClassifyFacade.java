package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.entity.BaseClassify;
import cn.powertime.iatp.vo.req.admin.BaseClassifyAddVo;
import cn.powertime.iatp.vo.req.admin.BaseClassifyEditVo;
import cn.powertime.iatp.vo.req.admin.BaseClassifySearchVo;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.resp.admin.BaseClassifyPageListVo;
import cn.powertime.iatp.vo.resp.admin.CommonTree;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseClassifyFacade {

    /**
     * 验证名称是否存在
     *
     * @param pid
     * @param type
     * @param name
     * @param id
     * @return
     */
    boolean checkNameOnly(Long pid, int type, String name, Long id);

    boolean add(BaseClassifyAddVo vo);

    boolean edit(BaseClassifyEditVo vo);

    Page<BaseClassifyPageListVo> list(ParamPageVo<BaseClassifySearchVo> vo);

    List<BaseClassify> listAll(int type);

    BaseClassify selectById(Long id);

    boolean batchDel(List<String> strings);

    boolean enableDisabled(Long id);

    List<CommonTree> tree();

}
