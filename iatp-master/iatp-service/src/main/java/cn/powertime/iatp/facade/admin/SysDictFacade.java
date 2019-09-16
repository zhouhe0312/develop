package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.entity.SysDict;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.SysDictAddVo;
import cn.powertime.iatp.vo.req.admin.SysDictEditVo;
import cn.powertime.iatp.vo.req.admin.SysDictListVo;
import cn.powertime.iatp.vo.resp.admin.SysDictVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
public interface SysDictFacade {

    /**
     * 验证权限名称及编码唯一性
     *
     * @param type
     * @param value
     * @param id
     * @param typeId
     * @return
     */
    boolean checkNameAndValueOnly(int type, String value, Long id, Long typeId);

    List<SysDict> selectByTypeId(Long typeId);

    SysDict selectById(@NotNull(message = "字典类型ID不能为空") Long id);

    boolean add(SysDictAddVo vo);

    boolean edit(SysDictEditVo vo);

    boolean del(Long id);

    Page<SysDictVo> list(ParamPageVo<SysDictListVo> vo);

    boolean batchDel(List<String> strings);
}
