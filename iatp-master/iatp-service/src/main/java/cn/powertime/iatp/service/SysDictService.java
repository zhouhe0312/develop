package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.SysDict;
import cn.powertime.iatp.vo.req.admin.SysDictListVo;
import cn.powertime.iatp.vo.resp.admin.SysDictVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
public interface SysDictService extends IService<SysDict> {

    Page<SysDictVo> selectPage(Page<SysDictVo> page, SysDictListVo params);

    boolean ownSave(SysDict dict);

    boolean ownUpdateById(SysDict dict);

    boolean del(SysDict dict);

    boolean batchDel(List<String> strings);
}
