package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.BaseStandard;
import cn.powertime.iatp.mapper.BaseStandardMapper;
import cn.powertime.iatp.service.BaseStandardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 知识库内容标准表 服务实现类
 * </p>
 *
 * @author yang xin
 * @since 2019-07-04
 */
@Service
public class BaseStandardServiceImpl extends ServiceImpl<BaseStandardMapper, BaseStandard> implements BaseStandardService {

    @Autowired
    private BaseStandardMapper baseStandardMapper;

    @Override
    public boolean add(BaseStandard standard) {
        return save(standard);
    }

    @Override
    public boolean edit(BaseStandard standard) {
        return updateById(standard);
    }

    @Override
    public boolean batchDel(List<String> strings) {
        Integer i = baseStandardMapper.batchDel(strings);
        return i > 0;
    }

}
