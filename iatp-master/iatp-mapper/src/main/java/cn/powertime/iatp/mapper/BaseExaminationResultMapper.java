package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.BaseExaminationResult;
import cn.powertime.iatp.vo.req.admin.ExaminationResultReqVo;
import cn.powertime.iatp.vo.resp.admin.ExaminationResultRespVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户考试结果表 Mapper 接口
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Mapper
public interface BaseExaminationResultMapper extends BaseMapper<BaseExaminationResult> {

    Page<ExaminationResultRespVo> selectPage(Page<ExaminationResultReqVo> page, @Param("vo") ExaminationResultReqVo vo);
}
