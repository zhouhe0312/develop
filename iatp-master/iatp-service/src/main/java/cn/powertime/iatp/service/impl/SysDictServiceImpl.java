package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.SysDict;
import cn.powertime.iatp.mapper.SysDictMapper;
import cn.powertime.iatp.service.SysDictService;
import cn.powertime.iatp.vo.req.admin.SysDictListVo;
import cn.powertime.iatp.vo.resp.admin.SysDictVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public Page<SysDictVo> selectPage(Page<SysDictVo> page, SysDictListVo params) {
        return sysDictMapper.mySelectPage(page,params);
    }

    @Override
    public boolean ownSave(SysDict dict) {
        return save(dict);
    }

    @Override
    public boolean ownUpdateById(SysDict dict) {
        return updateById(dict);
    }

    @Override
    public boolean del(SysDict dict) {
        return false;
    }

    @Override
    public boolean batchDel(List<String> strings) {
        Integer i = sysDictMapper.batchDel(strings);
        return i > 0;
    }

}
