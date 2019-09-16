package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.entity.BaseCourse;
import cn.powertime.iatp.vo.req.admin.*;
import cn.powertime.iatp.vo.resp.admin.KeyValueVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程（实验）表 服务类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
public interface BaseCourseFacade {

    /**
     * 验证名称是否存在
     *
     * @param type
     * @param name
     * @param id
     * @return
     */
    boolean checkNameOnly(int type, String name, Long id);

    /**
     * 查询所有课程和实验列表，返回KeyValue对象，key为ID,value为名称
     *
     * @return 返回KeyValueVo对象列表
     */
    List<KeyValueVo> selectList(Integer type);

    boolean add(BaseCourseAddVo vo);

    boolean edit(BaseCourseEditVo vo);

    Page<BaseCourse> list(ParamPageVo<BaseCourseSearchVo> vo);

    BaseCourse selectById(Long id);

    boolean batchDel(List<String> strings);

    boolean importExcel(MultipartFile file, BaseCourseUploadVo vo);
}
