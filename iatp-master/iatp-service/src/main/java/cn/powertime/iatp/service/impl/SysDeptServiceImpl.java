package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.SysDept;
import cn.powertime.iatp.mapper.SysDeptMapper;
import cn.powertime.iatp.service.SysDeptService;
import cn.powertime.iatp.vo.req.admin.SysDeptListVo;
import cn.powertime.iatp.vo.resp.admin.SysDeptPageListVo;
import cn.powertime.iatp.vo.resp.admin.SysDictVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-20
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public boolean ownSave(SysDept dept) {
        return save(dept);
    }

    @Override
    public boolean ownUpdateById(SysDept dept) {
        return updateById(dept);
    }

    @Override
    public boolean del(SysDept dept) {
        return updateById(dept);
    }

    @Override
    public IPage<SysDeptPageListVo> selectPage(Page<SysDictVo> page, SysDeptListVo params) {
       return sysDeptMapper.mySelectPage(page,params);
    }

    @Override
    public SysDept selectById(Long id) {
        return getById(id);
    }

    @Override
    public boolean batchDel(List<String> strings) {
        Integer i = sysDeptMapper.batchDel(strings);
        return i > 0;
    }
}
