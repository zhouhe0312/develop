package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.entity.SysDept;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.SysDeptAddVo;
import cn.powertime.iatp.vo.req.admin.SysDeptEditVo;
import cn.powertime.iatp.vo.req.admin.SysDeptListVo;
import cn.powertime.iatp.vo.resp.admin.DeptTree;
import cn.powertime.iatp.vo.resp.admin.SysDeptPageListVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-20
 */
public interface SysDeptFacade {

    /**
     * 验证名称及编码是否重复
     *
     * @param type
     * @param value
     * @param id
     * @return
     */
    boolean checkNameAndCodeOnly(int type, String value, Long id);

    boolean add(SysDeptAddVo addVo);

    boolean edit(SysDeptEditVo editVo);

    boolean del(Long id);

    IPage<SysDeptPageListVo> list(ParamPageVo<SysDeptListVo> vo);

    SysDept selectById(Long id);

    boolean batchDel(List<String> strings);

    List<DeptTree> tree(int type);
}
