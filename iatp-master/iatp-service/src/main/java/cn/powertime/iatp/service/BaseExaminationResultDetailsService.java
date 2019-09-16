package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.BaseExaminationResultDetails;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户考试结果详情表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseExaminationResultDetailsService extends IService<BaseExaminationResultDetails> {

    void batchAdd(List<BaseExaminationResultDetails> baseExaminationResultDetailses);
}
