package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.entity.BaseKnowledgeContent;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeContentAddVo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeContentEditVo;
import cn.powertime.iatp.vo.resp.admin.CommonTree;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 知识内容表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseKnowledgeContentFacade {

    /**
     * 验证名称是否重复
     *
     * @param father
     * @param name
     * @param id
     * @return
     */
    boolean checkNameOnly(Long father, String name, Long id);

    boolean add(BaseKnowledgeContentAddVo vo);

    boolean edit(BaseKnowledgeContentEditVo vo);

    BaseKnowledgeContent selectById(Long id);

    List<BaseKnowledgeContent> selectByPid(Long pid);

    boolean batchDel(List<String> strings);

    List<CommonTree> tree(String name, Long pid);

    boolean uploadPicture(Long id, String filename);

    /**
     * 上传内容
     *
     * @param file
     * @param pid
     * @return
     */
    boolean uploadContent(MultipartFile file, Long pid);

}
