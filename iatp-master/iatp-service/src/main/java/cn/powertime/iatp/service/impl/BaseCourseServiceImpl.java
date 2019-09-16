package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.entity.BaseChapelTest;
import cn.powertime.iatp.entity.BaseCourse;
import cn.powertime.iatp.mapper.BaseCourseMapper;
import cn.powertime.iatp.service.BaseCourseService;
import cn.powertime.iatp.vo.req.admin.BaseCourseImportVo;
import cn.powertime.iatp.vo.req.admin.BaseCourseSearchVo;
import cn.powertime.iatp.vo.req.admin.BaseCourseUploadVo;
import cn.powertime.iatp.vo.req.web.CoursePageListSearchVo;
import cn.powertime.iatp.vo.resp.web.CourseDetails;
import cn.powertime.iatp.vo.resp.web.IndexCourseList;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程（实验）表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Service
public class BaseCourseServiceImpl extends ServiceImpl<BaseCourseMapper, BaseCourse> implements BaseCourseService {

    @Autowired
    private BaseCourseMapper baseCourseMapper;

    @Override
    public boolean add(BaseCourse course) {
        return save(course);
    }

    @Override
    public boolean edit(BaseCourse course) {
        return updateById(course);
    }

    @Override
    public Page<BaseCourse> selectPage(Page<BaseCourse> page, BaseCourseSearchVo vo) {
        return baseCourseMapper.mySelectPage(page, vo);
    }

    @Override
    public BaseCourse selectById(Long id) {
        return getById(id);
    }

    @Override
    public boolean batchDel(List<String> strings) {
        Integer i = baseCourseMapper.batchDel(strings);
        return i > 0;
    }

    @Override
    public boolean importExcel(List<BaseCourseImportVo> result, BaseCourseUploadVo vo) {
        List<BaseCourse> collect = result.stream().map(item -> {
            int type = 0;
            if (item.getType().equals("课程")) {
                type = 1;
            } else if (item.getType().equals("实验")) {
                type = 2;
            }
            BaseCourse course = new BaseCourse();
            BeanUtils.copyProperties(item, course);
            course.setType(type);
            course.setStatus(Constants.STATUS_NORMAL);
            course.setCreateTime(LocalDateTime.now());
            course.setUpdateTime(LocalDateTime.now());
            course.setId(IdWorker.getId());
            course.setClassifyPid(vo.getClassifyPid());
            course.setClassifyId(vo.getClassifyId());
            return course;
        }).collect(Collectors.toList());
        return saveBatch(collect);
    }

    @Override
    public List<IndexCourseList> webIndexCourseList(Integer type) {
        return baseCourseMapper.webIndexCourseList(type);
    }

    @Override
    public Page<IndexCourseList> webSelectPage(Page<IndexCourseList> page, CoursePageListSearchVo params) {
        return baseCourseMapper.webSelectPage(page, params);
    }

    @Override
    public CourseDetails webDetails(Long id) {
        return baseCourseMapper.webDetails(id);
    }
}
