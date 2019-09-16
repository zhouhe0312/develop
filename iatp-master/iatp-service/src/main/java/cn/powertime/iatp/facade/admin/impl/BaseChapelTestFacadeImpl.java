package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.IatpConstants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.BaseChapelTest;
import cn.powertime.iatp.entity.BaseExaminationResult;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.BaseChapelTestFacade;
import cn.powertime.iatp.logging.EnumLogType;
import cn.powertime.iatp.logging.Logging;
import cn.powertime.iatp.service.BaseChapelTestService;
import cn.powertime.iatp.service.BaseExaminationPaperService;
import cn.powertime.iatp.service.BaseExaminationResultService;
import cn.powertime.iatp.utils.CollectionUtils;
import cn.powertime.iatp.vo.req.admin.BaseChapelTestAddVo;
import cn.powertime.iatp.vo.req.admin.BaseChapelTestEditVo;
import cn.powertime.iatp.vo.req.admin.BaseChapelTestSearchVo;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.resp.admin.BaseChapelTestPageListVo;
import cn.powertime.iatp.vo.resp.admin.KeyValueVo;
import cn.powertime.iatp.vo.resp.admin.TopicListVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 试卷表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
@Order(2)
public class BaseChapelTestFacadeImpl implements BaseChapelTestFacade {

    @Autowired
    private BaseChapelTestService baseChapelTestService;
    @Autowired
    private BaseExaminationResultService baseExaminationResultService;
    @Autowired
    private BaseExaminationPaperService baseExaminationPaperService;



    @Override

    public List<KeyValueVo> selectList() {

        QueryWrapper<BaseChapelTest> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        wrapper.orderByDesc("create_time");
        List<BaseChapelTest> list = baseChapelTestService.list(wrapper);

        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }

        return list.stream().map(item -> {
            String testType = IatpConstants.TEST_TYPE_MAP.get(String.valueOf(item.getTestType()));
            return KeyValueVo.builder()
                    .key(String.valueOf(item.getId()))
                    .value(item.getTitle() + "(" + testType + ")")
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    @Logging(code = "test.add",vars = {"vo.title","vo"},type = EnumLogType.ADD)
    public boolean add(BaseChapelTestAddVo vo) {
        if(vo.getTestType() == 7 && vo.getScore() != 100){
            throw new IatpException("综合试卷的分值必须是100分");
        }
        check(null,vo.getTestType(), vo.getCourseId(), vo.getChapterId(), vo.getSubsection(), vo.getCourseOneId(), vo.getCourseTwoId());
        BaseChapelTest baseChapelTest = new BaseChapelTest();
        BeanUtils.copyProperties(vo, baseChapelTest);
        baseChapelTest.setStatus(Constants.STATUS_NORMAL);
        baseChapelTest.setCreateTime(LocalDateTime.now());
        baseChapelTest.setUpdateTime(LocalDateTime.now());
        baseChapelTest.setId(IdWorker.getId());
        return baseChapelTestService.add(baseChapelTest);
    }

    @Override
    @Logging(code = "test.edit",vars = {"vo.title","vo"},type = EnumLogType.UPDATE)
    public boolean edit(BaseChapelTestEditVo vo) {
        if(vo.getTestType() == 7 && vo.getScore() != 100){
            throw new IatpException("综合试卷的分值必须是100分");
        }
        check(vo.getId(), vo.getTestType(), vo.getCourseId(), vo.getChapterId(), vo.getSubsection(), vo.getCourseOneId(), vo.getCourseTwoId());
        BaseChapelTest baseChapelTest = new BaseChapelTest();
        BeanUtils.copyProperties(vo, baseChapelTest);
        baseChapelTest.setUpdateTime(LocalDateTime.now());
        return baseChapelTestService.edit(baseChapelTest);
    }

    /**
     * 根基不同归属类型，试卷类型验证参数合法性
     *
     * @param testType    试卷类型
     * @param courseId    课程/实验ID
     * @param chapterId   章节ID
     * @param subsection  小节ID
     * @param courseOneId 综合考试(课程)
     * @param courseTwoId 综合考试(实验)
     */
    private void check(Long id, Integer testType, Long courseId, Long chapterId, Long subsection, Long courseOneId, Long courseTwoId) {
        if (testType == 1) {
            if (null == courseId) {
                throw new IatpException("课程ID不能为空");
            }
            if (null == chapterId) {
                throw new IatpException("课程章节ID不能为空");
            }
            if (null == subsection) {
                throw new IatpException("课程小节ID不能为空");
            }

            //随堂测试只能有一个
            QueryWrapper<BaseChapelTest> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("chapter_id",chapterId);
            queryWrapper.eq("course_id",courseId);
            queryWrapper.eq("subsection",subsection);
            queryWrapper.eq("test_type",testType);
            queryWrapper.ne("status",Constants.STATUS_DEL);
            if(null != id){
                queryWrapper.ne("id",id);
            }
            int count = baseChapelTestService.count(queryWrapper);
            if(count > 0){
                throw new IatpException("当前小节下已存在随堂测试");
            }

        }
        if (testType == 2) {
            if (null == courseId) {
                throw new IatpException("课程ID不能为空");
            }
            if (null == chapterId) {
                throw new IatpException("课程章节ID不能为空");
            }

            //单元测试是能有一个
            QueryWrapper<BaseChapelTest> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",courseId);
            queryWrapper.eq("chapter_id",chapterId);
            queryWrapper.eq("test_type",testType);
            queryWrapper.ne("status",Constants.STATUS_DEL);
            if(null != id){
                queryWrapper.ne("id",id);
            }
            int count = baseChapelTestService.count(queryWrapper);
            if(count > 0){
                throw new IatpException("当前章节下已存在单元测试");
            }
        }
        if (testType == 3) {
            if (null == courseId) {
                throw new IatpException("课程ID不能为空");
            }
        }
        if (testType == 4) {
            if (null == courseId) {
                throw new IatpException("实验ID不能为空");
            }
            if (null == chapterId) {
                throw new IatpException("实验章节ID不能为空");
            }
            if (null == subsection) {
                throw new IatpException("实验小节ID不能为空");
            }

            //随堂测试是能有一个
            QueryWrapper<BaseChapelTest> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("chapter_id",chapterId);
            queryWrapper.eq("subsection",subsection);
            queryWrapper.eq("course_id",courseId);
            queryWrapper.eq("test_type",testType);
            queryWrapper.ne("status",Constants.STATUS_DEL);
            if(null != id){
                queryWrapper.ne("id",id);
            }
            int count = baseChapelTestService.count(queryWrapper);
            if(count > 0){
                throw new IatpException("当前小节下已存在随堂测试");
            }

        }
        if (testType == 5) {
            if (null == courseId) {
                throw new IatpException("实验ID不能为空");
            }
            if (null == chapterId) {
                throw new IatpException("实验章节ID不能为空");
            }

            //单元测试是能有一个
            QueryWrapper<BaseChapelTest> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",courseId);
            queryWrapper.eq("test_type",testType);
            queryWrapper.eq("chapter_id",chapterId);
            queryWrapper.ne("status",Constants.STATUS_DEL);
            if(null != id){
                queryWrapper.ne("id",id);
            }
            int count = baseChapelTestService.count(queryWrapper);
            if(count > 0){
                throw new IatpException("当前章节下已存在单元测试");
            }

        }
        if (testType == 6) {
            if (null == courseId) {
                throw new IatpException("实验ID不能为空");
            }
        }
        if (testType == 7) {
            if (courseOneId == null) {
                throw new IatpException("课程ID不能为空");
            }
        }
    }

    @Override
    public Page<BaseChapelTestPageListVo> list(ParamPageVo<BaseChapelTestSearchVo> vo) {
        Page<BaseChapelTest> page = new Page<>(vo.getPage().getCurrent(), vo.getPage().getSize());
        return baseChapelTestService.selectPage(page, vo.getParams());
    }

    @Override
    public List<BaseChapelTest> listAll() {
        QueryWrapper<BaseChapelTest> queryWrapper = new QueryWrapper();
        queryWrapper.eq("status", Constants.STATUS_NORMAL);
        return baseChapelTestService.list(queryWrapper);
    }

    @Override
    public BaseChapelTest selectById(Long id) {
        return baseChapelTestService.selectById(id);
    }

    @Override
    @Logging(code = "test.del",vars = {"","strings"},type = EnumLogType.DEL)
    public boolean batchDel(List<String> strings) {
        QueryWrapper<BaseExaminationResult> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("test_paper_id", strings);
        Integer count = baseExaminationResultService.count(queryWrapper);
        if (count > 0) {
            throw new IatpException("选中的试卷有被用户考试不能删除");
        }
        //根据试卷删除试题
        baseExaminationPaperService.delByTestIds(strings);
        return baseChapelTestService.batchDel(strings);
    }

    @Override
    @Logging(code = "test.enableDisabled",vars = {"","id"},type = EnumLogType.UPDATE)
    public boolean enableDisabled(String id) {
        BaseChapelTest topic = baseChapelTestService.getById(Long.valueOf(id));
        if (topic.getStatus().equals(Constants.STATUS_NORMAL)) {
            QueryWrapper<BaseExaminationResult> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("test_paper_id", id);
            Integer count = baseExaminationResultService.count(queryWrapper);
            if (count > 0) {
                throw new IatpException("选中的试卷有被用户考试不能禁用");
            }
            topic.setStatus(Constants.STATUS_DISABLED);
        }else if (topic.getStatus().equals(Constants.STATUS_DISABLED)) {
            topic.setStatus(Constants.STATUS_NORMAL);
        }

        return baseChapelTestService.updateById(topic);
    }

    @Override
    public List<TopicListVo> getTopicListByChapelId(Long id) {
        return baseChapelTestService.getTopicListByChapelId(id);
    }


}
