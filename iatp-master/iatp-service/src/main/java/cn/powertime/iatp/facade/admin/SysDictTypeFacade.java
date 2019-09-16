package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.entity.SysDictType;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.SysDictTypeListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : liqi
 * Date: 2018-11-26
 * Time: 9:55
 */
public interface SysDictTypeFacade {

    /**
     * 验证权限名称及编码唯一性
     *
     * @param name
     * @param id
     * @return
     */
    boolean checkNameOnly(String name, Long id);

    boolean add(String name);

    boolean edit(String name, Long id);

    boolean del(Long id);

    List<SysDictType> selectList();

    SysDictType selectById(Long id);

    Page<SysDictType> list(ParamPageVo<SysDictTypeListVo> vo);
}
