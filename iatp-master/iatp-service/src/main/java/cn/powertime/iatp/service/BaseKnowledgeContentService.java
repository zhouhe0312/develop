package cn.powertime.iatp.service;

import cn.powertime.iatp.entity.BaseKnowledgeContent;
import cn.powertime.iatp.vo.req.admin.BaseContentUploadVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 知识内容表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseKnowledgeContentService extends IService<BaseKnowledgeContent> {

    boolean add(BaseKnowledgeContent content);

    boolean edit(BaseKnowledgeContent content);

    BaseKnowledgeContent selectById(Long id);

    boolean batchDel(List<String> strings);

    boolean uploadContent(List<BaseContentUploadVo> result, Long pid);

}
