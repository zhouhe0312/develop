package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.BaseStandard;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 知识库内容标准表 服务类
 * </p>
 *
 * @author yang xin
 * @since 2019-07-04
 */
public interface BaseStandardService extends IService<BaseStandard> {

    boolean add(BaseStandard standard);

    boolean edit(BaseStandard standard);

    boolean batchDel(List<String> strings);

}
