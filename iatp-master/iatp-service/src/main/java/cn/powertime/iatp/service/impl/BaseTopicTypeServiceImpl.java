package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.entity.BaseTopicType;
import cn.powertime.iatp.mapper.BaseTopicTypeMapper;
import cn.powertime.iatp.service.BaseTopicTypeService;
import cn.powertime.iatp.vo.req.admin.BaseTopicTypeSearchVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 题库类型表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Service
public class BaseTopicTypeServiceImpl extends ServiceImpl<BaseTopicTypeMapper, BaseTopicType> implements BaseTopicTypeService {

    @Autowired
    private BaseTopicTypeMapper baseTopicTypeMapper;

    @Override
    public boolean add(BaseTopicType baseTopicType) {
        return save(baseTopicType);
    }

    @Override
    public boolean edit(BaseTopicType baseTopicType) {
        return updateById(baseTopicType);
    }

    @Override
    public Page<BaseTopicType> selectPage(Page<BaseTopicType> page, BaseTopicTypeSearchVo params) {
        return baseTopicTypeMapper.mySelectPage(page,params);
    }

    @Override
    public BaseTopicType selectById(Long id) {
        return getById(id);
    }

    @Override
    public boolean batchDel(List<String> strings) {
        Integer i = baseTopicTypeMapper.batchDel(strings);
        return i > 0;
    }

    @Override
    public List<BaseTopicType> listAll() {
        QueryWrapper<BaseTopicType> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("status", Constants.STATUS_DEL);
        return list(queryWrapper);
    }
}
