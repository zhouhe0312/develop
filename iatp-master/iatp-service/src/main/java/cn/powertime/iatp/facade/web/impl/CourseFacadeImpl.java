package cn.powertime.iatp.facade.web.impl;

import cn.powertime.iatp.authres.security.SecurityUtils;
import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.IatpConstants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.BaseCourse;
import cn.powertime.iatp.entity.BaseUserLearningRecord;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.web.CourseFacade;
import cn.powertime.iatp.service.BaseChapterService;
import cn.powertime.iatp.service.BaseCourseService;
import cn.powertime.iatp.service.BaseResourceService;
import cn.powertime.iatp.service.BaseUserLearningRecordService;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.web.CoursePageListSearchVo;
import cn.powertime.iatp.vo.req.web.RecordVo;
import cn.powertime.iatp.vo.req.web.ResourceSearchVo;
import cn.powertime.iatp.vo.resp.web.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class CourseFacadeImpl implements CourseFacade {

    @Autowired
    private BaseCourseService baseCourseService;
    @Autowired
    private BaseUserLearningRecordService baseUserLearningRecordService;
    @Autowired
    private BaseChapterService baseChapterService;
    @Autowired
    private BaseResourceService baseResourceService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Page<IndexCourseList> selectPage(ParamPageVo<CoursePageListSearchVo> vo) {
        Page<IndexCourseList> page = new Page<>(vo.getPage().getCurrent(), vo.getPage().getSize());
        return baseCourseService.webSelectPage(page,vo.getParams());
    }

    @Override
    public boolean pvAdd(Long id) {
        BaseCourse course = baseCourseService.selectById(id);
        long pv = course.getCourseWarePv();
        pv++;
        course.setCourseWarePv(pv);
        return baseCourseService.updateById(course);
    }

    @Override
    public CourseDetails details(Long id) {
        return baseCourseService.webDetails(id);
    }

    @Override
    public boolean record(RecordVo vo) {

        Long userId = SecurityUtils.getCurrentUserId();
        Long resourceId = vo.getResourceId();

        String userIdAndResourceId = String.valueOf(userId) + resourceId;
        Long result = redisTemplate.boundSetOps(IatpConstants.RECORD_KEY).add(userIdAndResourceId);

        if (result == null) {
            log.error("使用Redis的add方法获得的返回值为空，null when used in pipeline / transaction. 即redis使用了pipeline模式或者是事务模式");
            throw new IatpException("服务器出错了");
        }

        if(result != 0){
            BaseUserLearningRecord record = new BaseUserLearningRecord();
            BeanUtils.copyProperties(vo,record);
            record.setId(IdWorker.getId());
            record.setStatus(Constants.STATUS_NORMAL);
            record.setCreateTime(LocalDateTime.now());
            record.setUpdateTime(LocalDateTime.now());
            record.setUserId(SecurityUtils.getCurrentUserId());

            return baseUserLearningRecordService.add(record);
        }

        QueryWrapper<BaseUserLearningRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("resource_id",resourceId);
        queryWrapper.eq("status",Constants.STATUS_NORMAL);
        BaseUserLearningRecord record = baseUserLearningRecordService.getOne(queryWrapper);

        record.setSchedule(vo.getSchedule());
        record.setType(vo.getType());
        record.setUpdateTime(LocalDateTime.now());
        return baseUserLearningRecordService.edit(record);

    }

    @Override
    public List<ChapterListVo> chapterList(Long id,Long uid) {
//        try {
//            uid = SecurityUtils.getCurrentUserId();
//        }catch (Exception e){
//            uid = 0L;
//        }

        List<ChapterListVo> list = baseChapterService.chapterList(id,uid);
        list.forEach(i->{
            if(StringUtils.equals("0",i.getTempStatus())){
                i.setStudyStatus(0);
            }else{
                int size = Splitter.on(",").splitToList(i.getTempStatus()).size();
                if(i.getNum() == size && i.getTempStatus().indexOf("1") < 0 ){
                    i.setStudyStatus(2);
                }else{
                    i.setStudyStatus(1);
                }
            }
        });
        return getTrees(list);
    }

    @Override
    public CourseResourceRespListVo courseResourceList(ResourceSearchVo vo) {

        List<CourseResourceListVo> list = baseResourceService.courseResourceList(vo,SecurityUtils.getCurrentUserId());

        List<CourseResourceListVo> unitList = list.stream().filter(item -> StringUtils.equals("2", item.getType() + "")).collect(Collectors.toList());

        List<CourseResourceListVo> resourceList = list.stream().filter(item -> !StringUtils.equals("2", item.getType() + "")).collect(Collectors.toList());

        CourseResourceRespListVo courseResourceRespListVo = CourseResourceRespListVo.builder()
                .unitTest(unitList)
                .resourceList(getCourseResourceTrees(resourceList))
                .build();
        return courseResourceRespListVo;
    }

    @Override
    public List<ExperimentResourceRespListVo> experimentResourceList(ResourceSearchVo vo) {
        List<ExperimentResourceRespListVo> list = baseResourceService.experimentResourceList(vo,SecurityUtils.getCurrentUserId());
        return  getExperimentResourceTrees(list);
    }

    private List<ExperimentResourceRespListVo> getExperimentResourceTrees(List<ExperimentResourceRespListVo> list) {
        List<ExperimentResourceRespListVo> rootList = list.stream().filter(item -> StringUtils.equals("0", item.getPid()+"")).collect(Collectors.toList());
        for(ExperimentResourceRespListVo root : rootList) {
            getExperimentResourceChilds(list,root);
        }
        return rootList;
    }

    private void getExperimentResourceChilds(List<ExperimentResourceRespListVo> childList,  ExperimentResourceRespListVo chapter){
        //获取实训
        List<ExperimentResourceRespListVo> sx = childList.stream().filter(item -> StringUtils.equals("4", item.getResourceType() + "")).collect(Collectors.toList());
        //获取非实训
        List<ExperimentResourceRespListVo> fsx = childList.stream().filter(item -> !StringUtils.equals("4", item.getResourceType() + "")).collect(Collectors.toList());
        //将实训放在资源的最后一个
        fsx.addAll(sx);
        List<ExperimentResourceRespListVo> cList = fsx.stream()
                .filter(item -> StringUtils.equals(item.getPid()+"", chapter.getId()+""))
                .collect(Collectors.toList());
        if(cList == null || cList.isEmpty()) {
            return;
        }
        chapter.setChild(cList);

    }

    private List<CourseResourceListVo> getCourseResourceTrees(List<CourseResourceListVo> list) {
        List<CourseResourceListVo> rootList = list.stream().filter(item -> StringUtils.equals("0", item.getPid()+"")).collect(Collectors.toList());
        for(CourseResourceListVo root : rootList) {
            getCourseResourceChilds(list,root);
        }
        return rootList;
    }

    private void getCourseResourceChilds(List<CourseResourceListVo> childList,  CourseResourceListVo chapter){

        List<CourseResourceListVo> listVos = childList.stream().sorted(Comparator.comparing(CourseResourceListVo::getSort)).collect(Collectors.toList());

        List<CourseResourceListVo> cList = listVos.stream()
                .filter(item -> StringUtils.equals(item.getPid()+"", chapter.getId()+""))
                .collect(Collectors.toList());
        if(cList == null || cList.isEmpty()) {
            return;
        }
        chapter.setChild(cList);

    }


    private List<ChapterListVo> getTrees(List<ChapterListVo> list) {
        List<ChapterListVo> rootList = list.stream().filter(item -> StringUtils.equals("0", item.getPid()+"")).collect(Collectors.toList());
        for(ChapterListVo root : rootList) {
            getChilds(list,root);
        }
        return rootList;
    }

    private void getChilds(List<ChapterListVo> childList,  ChapterListVo chapter){

        List<ChapterListVo> cList = childList.stream()
                .filter(item -> StringUtils.equals(item.getPid()+"", chapter.getId()+""))
                .collect(Collectors.toList());
        if(cList == null || cList.isEmpty()) {
            return;
        }
        chapter.setChild(cList);

    }
}
