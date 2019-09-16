package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.BaseChapelTest;
import cn.powertime.iatp.entity.BaseChapter;
import cn.powertime.iatp.entity.BaseCourse;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.BaseCourseFacade;
import cn.powertime.iatp.logging.EnumLogType;
import cn.powertime.iatp.logging.Logging;
import cn.powertime.iatp.service.BaseChapelTestService;
import cn.powertime.iatp.service.BaseChapterService;
import cn.powertime.iatp.service.BaseCourseService;
import cn.powertime.iatp.utils.CollectionUtils;
import cn.powertime.iatp.utils.excel.EasyExcelUtils;
import cn.powertime.iatp.utils.excel.ExcelListener;
import cn.powertime.iatp.vo.req.admin.*;
import cn.powertime.iatp.vo.resp.admin.KeyValueVo;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程（实验）表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
@Order(2)
public class BaseCourseFacadeImpl implements BaseCourseFacade {

    @Autowired
    private BaseCourseService baseCourseService;

    @Autowired
    private BaseChapterService baseChapterService;

    @Autowired
    private BaseChapelTestService baseChapelTestService;

    @Override
    public boolean checkNameOnly(int type, String name, Long id) {
        QueryWrapper<BaseCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type);
        wrapper.eq("course_name", name);
        wrapper.eq("status", Constants.STATUS_NORMAL);
        if (null != id) {
            wrapper.ne("id", id);
        }
        return baseCourseService.count(wrapper) > 0;
    }

    @Override
    public List<KeyValueVo> selectList(Integer type) {
        QueryWrapper<BaseCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        if (type != 0) {
            wrapper.eq("type", type);
        }

        wrapper.orderByDesc("create_time");
        List<BaseCourse> courseList = baseCourseService.list(wrapper);
        if (CollectionUtils.isEmpty(courseList)) {
            return Lists.newArrayList();
        }

        return courseList.stream().map(item -> {
            String typeStr = item.getType() == 1 ? "课程" : "实验";
            return KeyValueVo.builder()
                    .key(String.valueOf(item.getId()))
                    .value(item.getCourseName() + "(" + typeStr + ")")
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    @Logging(code = "course.add", vars = {"vo.courseName", "vo"}, type = EnumLogType.ADD)
    public boolean add(BaseCourseAddVo vo) {
        boolean b = checkNameOnly(vo.getType(), vo.getCourseName(), null);
        if (b) {
            throw new IatpException("名称已经存在");
        }
        BaseCourse course = new BaseCourse();
        BeanUtils.copyProperties(vo, course);
        course.setStatus(Constants.STATUS_NORMAL);
        course.setCreateTime(LocalDateTime.now());
        course.setUpdateTime(LocalDateTime.now());
        course.setId(IdWorker.getId());
        return baseCourseService.add(course);
    }

    @Override
    @Logging(code = "course.edit", vars = {"vo.courseName", "vo"}, type = EnumLogType.UPDATE)
    public boolean edit(BaseCourseEditVo vo) {
        boolean b = checkNameOnly(vo.getType(), vo.getCourseName(), vo.getId());
        if (b) {
            throw new IatpException("名称已经存在");
        }
        BaseCourse course = new BaseCourse();
        BeanUtils.copyProperties(vo, course);
        course.setUpdateTime(LocalDateTime.now());
        return baseCourseService.edit(course);
    }

    @Override
    public Page<BaseCourse> list(ParamPageVo<BaseCourseSearchVo> vo) {
        Page<BaseCourse> page = new Page<>(vo.getPage().getCurrent(), vo.getPage().getSize());
        return baseCourseService.selectPage(page, vo.getParams());
    }

    @Override
    public BaseCourse selectById(Long id) {
        return baseCourseService.selectById(id);
    }

    @Override
    @Logging(code = "course.del", vars = {"", "strings"}, type = EnumLogType.DEL)
    public boolean batchDel(List<String> strings) {
        QueryWrapper<BaseChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constants.STATUS_NORMAL);
        queryWrapper.in("course_id", strings);
        Integer count = baseChapterService.count(queryWrapper);
        if (count > 0) {
            throw new IatpException("选中的课程有被章节引用不能删除");
        }

        QueryWrapper<BaseChapelTest> queryWrapperTest = new QueryWrapper<>();
        queryWrapperTest.eq("status", Constants.STATUS_NORMAL);
        queryWrapperTest.in("course_id", strings);
        Integer countTest = baseChapelTestService.count(queryWrapperTest);
        if (countTest > 0) {
            throw new IatpException("选中的课程有被试卷引用不能删除");
        }

        return baseCourseService.batchDel(strings);
    }

    @Override
    public boolean importExcel(MultipartFile file, BaseCourseUploadVo vo) {
        List<BaseCourseImportVo> result;
        try {
            ExcelListener<BaseCourseImportVo> excelListener = new ExcelListener();
            ExcelReader reader = EasyExcelUtils.getReader(file, excelListener);
            reader.read(new Sheet(1, 1, BaseCourseImportVo.class));
            result = excelListener.getDatas();
            if (CollectionUtils.isNotEmpty(result)) {
                Set<String> names = new HashSet();
                result.forEach(item ->{
                    if(!names.add(item.getCourseName().trim())){
                        throw new IatpException("名称“"+item.getCourseName()+"”在模板中有重复记录");
                    }
                    if(StringUtils.isEmpty(item.getType()) || StringUtils.isEmpty(item.getCourseName())){
                        throw new IatpException("类型或名称不能为空");
                    }
                    if(item.getCourseName().length() > 50){
                        throw new IatpException("名称不能超过50个字符");
                    }
                    if(StringUtils.isNotEmpty(item.getIntroduce()) && item.getIntroduce().length() > 200){
                        throw new IatpException("简介不能超过200个字符");
                    }
                    if(!item.getType().trim().equals("课程") && !item.getType().trim().equals("实验")){
                        throw new IatpException("类型输入错误");
                    }
                    int type = 0;
                    if (item.getType().trim().equals("课程")) {
                        type = 1;
                    } else if (item.getType().trim().equals("实验")) {
                        type = 2;
                    }
                    boolean b = checkNameOnly(type, item.getCourseName().trim(), null);
                    if (b) {
                        throw new IatpException("名称“"+item.getCourseName()+"”系统中已经存在");
                    }
                });
                return baseCourseService.importExcel(result, vo);
            } else {
                throw new IatpException("读取解析表格数据失败！");
            }
        } catch (IatpException e) {
            throw new IatpException(e.getMessage(),e);
        }
    }
}
