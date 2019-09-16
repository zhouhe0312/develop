package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.BaseResource;
import cn.powertime.iatp.vo.req.web.ResourceSearchVo;
import cn.powertime.iatp.vo.resp.web.CourseResourceListVo;
import cn.powertime.iatp.vo.resp.web.ExperimentResourceRespListVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程（实验）资源表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseResourceService extends IService<BaseResource> {

    BaseResource getMaxSort();

    boolean add(BaseResource baseResource);

    boolean edit(BaseResource baseResource);

    boolean batchDel(List<String> strings);

    List<CourseResourceListVo> courseResourceList(ResourceSearchVo vo,Long uid);

    List<ExperimentResourceRespListVo> experimentResourceList(ResourceSearchVo vo,Long uid);
}
