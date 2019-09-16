package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.SysDictType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
public interface SysDictTypeService extends IService<SysDictType> {

    Page<SysDictType> selectPage(Page<SysDictType> page, String name);

    boolean ownSave(SysDictType type);

    boolean ownUpdateById(SysDictType type);

    boolean del(SysDictType type);

}
