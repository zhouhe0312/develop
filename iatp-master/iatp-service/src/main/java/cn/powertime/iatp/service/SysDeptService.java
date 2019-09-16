package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.SysDept;
import cn.powertime.iatp.vo.req.admin.SysDeptListVo;
import cn.powertime.iatp.vo.resp.admin.SysDeptPageListVo;
import cn.powertime.iatp.vo.resp.admin.SysDictVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-20
 */
public interface SysDeptService extends IService<SysDept> {

    boolean ownSave(SysDept dept);

    boolean ownUpdateById(SysDept dept);

    boolean del(SysDept dept);

    IPage<SysDeptPageListVo> selectPage(Page<SysDictVo> page, SysDeptListVo params);

    SysDept selectById(Long id);

    boolean batchDel(List<String> strings);
}
