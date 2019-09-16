package cn.powertime.iatp.mapper;

import cn.powertime.iatp.entity.BaseUserCollect;
import cn.powertime.iatp.vo.req.admin.UserCollectReqListVo;
import cn.powertime.iatp.vo.resp.admin.UserCollectRespListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户收藏表 Mapper 接口
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Mapper
public interface BaseUserCollectMapper extends BaseMapper<BaseUserCollect> {

    Page<UserCollectRespListVo> selectPage(Page<UserCollectReqListVo> page, @Param("vo") UserCollectReqListVo vo);
}
