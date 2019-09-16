package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.BaseClassify;
import cn.powertime.iatp.mapper.BaseClassifyMapper;
import cn.powertime.iatp.service.BaseClassifyService;
import cn.powertime.iatp.vo.req.admin.BaseClassifySearchVo;
import cn.powertime.iatp.vo.resp.admin.BaseClassifyPageListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Service
public class BaseClassifyServiceImpl extends ServiceImpl<BaseClassifyMapper, BaseClassify> implements BaseClassifyService {

    @Autowired
    private BaseClassifyMapper baseClassifyMapper;

    @Override
    public boolean add(BaseClassify classify) {
        return save(classify);
    }

    @Override
    public boolean edit(BaseClassify classify) {
        return updateById(classify);
    }

    @Override
    public Page<BaseClassifyPageListVo> selectPage(Page<BaseClassifyPageListVo> page, BaseClassifySearchVo params) {
        return baseClassifyMapper.mySelectPage(page,params);
    }

    @Override
    public BaseClassify selectById(Long id) {
        return getById(id);
    }

    @Override
    public boolean batchDel(List<String> strings) {
        Integer i = baseClassifyMapper.batchDel(strings);
        return i > 0;
    }
}
