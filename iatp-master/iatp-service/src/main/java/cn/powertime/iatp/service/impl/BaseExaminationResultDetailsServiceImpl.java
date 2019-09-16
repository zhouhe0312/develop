package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.BaseExaminationResultDetails;
import cn.powertime.iatp.mapper.BaseExaminationResultDetailsMapper;
import cn.powertime.iatp.service.BaseExaminationResultDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户考试结果详情表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Service
public class BaseExaminationResultDetailsServiceImpl extends ServiceImpl<BaseExaminationResultDetailsMapper, BaseExaminationResultDetails> implements BaseExaminationResultDetailsService {

    @Override
    public void batchAdd(List<BaseExaminationResultDetails> baseExaminationResultDetailses) {
        saveBatch(baseExaminationResultDetailses);
    }
}
