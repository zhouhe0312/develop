package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.BaseResource;
import cn.powertime.iatp.mapper.BaseResourceMapper;
import cn.powertime.iatp.service.BaseResourceService;
import cn.powertime.iatp.vo.req.web.ResourceSearchVo;
import cn.powertime.iatp.vo.resp.web.CourseResourceListVo;
import cn.powertime.iatp.vo.resp.web.ExperimentResourceRespListVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程（实验）资源表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Service
public class BaseResourceServiceImpl extends ServiceImpl<BaseResourceMapper, BaseResource> implements BaseResourceService {

    @Autowired
    private BaseResourceMapper baseResourceMapper;

    @Override
    public BaseResource getMaxSort() {
        return baseResourceMapper.getMaxSort();
    }

    @Override
    public boolean add(BaseResource baseResource) {
        return save(baseResource);
    }

    @Override
    public boolean edit(BaseResource baseResource) {
        return updateById(baseResource);
    }

    @Override
    public boolean batchDel(List<String> strings) {
        Integer i = baseResourceMapper.batchDel(strings);
        return i > 0;
    }

    @Override
    public List<CourseResourceListVo> courseResourceList(ResourceSearchVo vo,Long uid) {
        return baseResourceMapper.courseResourceList(vo,uid);
    }

    @Override
    public List<ExperimentResourceRespListVo> experimentResourceList(ResourceSearchVo vo,Long uid) {
        return baseResourceMapper.experimentResourceList(vo,uid);
    }

}
