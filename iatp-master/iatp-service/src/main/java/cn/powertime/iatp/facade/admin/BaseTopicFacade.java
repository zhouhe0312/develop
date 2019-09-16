package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.entity.BaseTopic;
import cn.powertime.iatp.vo.req.admin.BaseTopicAddVo;
import cn.powertime.iatp.vo.req.admin.BaseTopicEditVo;
import cn.powertime.iatp.vo.req.admin.BaseTopicSearchVo;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.resp.admin.BaseTopicPageListVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 题库表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseTopicFacade {

    boolean add(BaseTopicAddVo vo);

    boolean edit(BaseTopicEditVo vo);

    Page<BaseTopicPageListVo> list(ParamPageVo<BaseTopicSearchVo> vo);

    List<BaseTopic> listAll(Long typeId);

    BaseTopic selectById(Long id);

    boolean batchDel(List<String> strings);

    boolean enableDisabled(String id);

    boolean  importExcel(MultipartFile file, Long typeId);
}
