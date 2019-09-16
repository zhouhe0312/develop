package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.BaseResource;
import cn.powertime.iatp.vo.req.web.ResourceSearchVo;
import cn.powertime.iatp.vo.resp.web.CourseResourceListVo;
import cn.powertime.iatp.vo.resp.web.ExperimentResourceRespListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程（实验）资源表 Mapper 接口
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Mapper
public interface BaseResourceMapper extends BaseMapper<BaseResource> {

    BaseResource getMaxSort();

    Integer batchDel(@Param("ids") List<String> strings);

    List<CourseResourceListVo> courseResourceList(@Param("vo") ResourceSearchVo vo,@Param("uid") Long uid);

    List<ExperimentResourceRespListVo> experimentResourceList(@Param("vo")ResourceSearchVo vo,@Param("uid")Long uid);
}
