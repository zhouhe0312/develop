package cn.powertime.iatp.facade.web.impl;

import cn.powertime.iatp.authres.security.SecurityUtils;
import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.config.ScheduledConfig;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.*;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.web.ExamFacade;
import cn.powertime.iatp.service.*;
import cn.powertime.iatp.thread.SubmitPaperThread;
import cn.powertime.iatp.utils.CollectionUtils;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.web.CourseExamSeachVo;
import cn.powertime.iatp.vo.req.web.SubmitPaperTopicListVo;
import cn.powertime.iatp.vo.req.web.SubmitPaperVo;
import cn.powertime.iatp.vo.req.web.WrongQuestionsSeachVo;
import cn.powertime.iatp.vo.resp.web.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.NewThreadAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class ExamFacadeImpl implements ExamFacade {

    @Autowired
    private BaseUserCollectService baseUserCollectService;
    @Autowired
    private BaseExaminationPaperService baseExaminationPaperService;
    @Autowired
    private ScheduledConfig scheduledConfig;
    @Autowired
    private BaseExaminationResultDetailsService baseExaminationResultDetailsService;
    @Autowired
    private BaseTopicService baseTopicService;
    @Autowired
    private BaseWrongQuestionsService baseWrongQuestionsService;
    @Autowired
    private BaseChapelTestService baseChapelTestService;
    @Autowired
    private BaseExaminationResultService baseExaminationResultService;
    /**
     * 类型值映射
     */
    private final static Map<Integer,Integer> TEST_TYPE_MAP = ImmutableMap.of(3,1,6,2,7,3);

    @Override
    public Page<CourseExamPageListVo> webCourseExamList(ParamPageVo<CourseExamSeachVo> vo) {
        Page<BaseChapelTest> page = new Page<>(vo.getPage().getCurrent(), vo.getPage().getSize());
        return baseChapelTestService.webCourseExamList(page, vo.getParams(), SecurityUtils.getCurrentUserId());
    }

    @Override
    public boolean collect(Long id, Integer type) {
        if (type == 1) {
            BaseChapelTest chapelTest = baseChapelTestService.getById(id);
            QueryWrapper<BaseUserCollect> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status",Constants.STATUS_NORMAL);
            queryWrapper.eq("user_id",SecurityUtils.getCurrentUserId());
            queryWrapper.eq("course_id",id);
            queryWrapper.eq("type",TEST_TYPE_MAP.get(chapelTest.getTestType()));
            int count =baseUserCollectService.count(queryWrapper);
            if(count > 0){
                return true;
            }
            BaseUserCollect collect = BaseUserCollect.builder()
                    .courseId(id)
                    .createTime(LocalDateTime.now())
                    .id(IdWorker.getId())
                    .status(Constants.STATUS_NORMAL)
                    .type(TEST_TYPE_MAP.get(chapelTest.getTestType()))
                    .updateTime(LocalDateTime.now())
                    .userId(SecurityUtils.getCurrentUserId())
                    .build();
            return baseUserCollectService.add(collect);
        }
        QueryWrapper<BaseUserCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        queryWrapper.eq("user_id", SecurityUtils.getCurrentUserId());
        return baseUserCollectService.remove(queryWrapper);
    }

    @Override
    public PaperDetailsVo paperDetail(Long id) {
        BaseChapelTest chapelTest = baseChapelTestService.selectById(id);
        //根据试卷类型获取获取课程ID
        Long courseId;
        if(chapelTest.getTestType() == 7){
            //综合考试
            courseId = chapelTest.getCourseOneId();
        }else{
            courseId = chapelTest.getCourseId();
        }
        //获取题干列表
        List<TopicVo> list = baseExaminationPaperService.getTopics(id);
        if(CollectionUtils.isEmpty(list)){
            throw new IatpException("当前试卷没有完成组卷，不能考试。");
        }
        PaperDetailsVo result = PaperDetailsVo.builder()
                .title(chapelTest.getTitle())
                .score(chapelTest.getScore())
                .paperDuration(chapelTest.getPaperDuration())
                .id(chapelTest.getId())
                .topicList(list)
                .courseId(courseId)
                .type(getType(chapelTest.getTestType()))
                .build();
        return result;
    }

    private Integer getType(Integer type){
        if(type == 1 || type == 2 || type == 3 ){
            return 1;
        }
        if(type == 4 || type == 5 || type == 6 ){
            return 2;
        }
        if(type == 7 ){
            return 3;
        }
        return null;
    }

    @Override
    public PaperDetailsVo paperResultDetail(Long id,Long resultId) {
        PaperDetailsVo result = baseChapelTestService.paperResult(id,SecurityUtils.getCurrentUserId());
        List<TopicVo> list = baseExaminationPaperService.getTopicsResult(id,resultId,SecurityUtils.getCurrentUserId());
        result.setTopicList(list);
        return result;
    }

    @Override
    public SubmitPaperRespVo submitPaper(SubmitPaperVo vo) {
        //获取所有题库试题ID集合
        List<Long> topicIds = vo.getList().stream().map(SubmitPaperTopicListVo::getTopicId).collect(Collectors.toList());
        //根据题库试题ID集合获取试卷试题
        Collection<BaseTopic> baseTopics = baseTopicService.listByIds(topicIds);
        //试卷试题转成map  key-id  value-answer
        Map<Long, String> idAnswerMap = baseTopics.stream().collect(Collectors.toMap(BaseTopic::getId, BaseTopic::getTopicAnswer));

        final Integer[] score = {0};

        Long resultId = IdWorker.getId();

        List<BaseWrongQuestions> wrongQuestionsList = Lists.newArrayList();
        List<BaseWrongQuestions> updateWrongQuestionsList = Lists.newArrayList();

        //查看错题库中是否有当前试题
        QueryWrapper<BaseWrongQuestions> baseWrongQuestionsQueryWrapper = new QueryWrapper<>();
        baseWrongQuestionsQueryWrapper.eq("user_id",SecurityUtils.getCurrentUserId());
        baseWrongQuestionsQueryWrapper.eq("status",Constants.STATUS_NORMAL);
        baseWrongQuestionsQueryWrapper.eq("course_id",vo.getCourseId());
        baseWrongQuestionsQueryWrapper.eq("test_paper_id",vo.getId());

        List<BaseWrongQuestions> wrongQuestions = baseWrongQuestionsService.list(baseWrongQuestionsQueryWrapper);
        Set<Long> wrongTopicIList = wrongQuestions.stream().map(BaseWrongQuestions::getTopicId).collect(Collectors.toSet());
        Map<Long, Long> wrongTopicAndIdMapList = wrongQuestions.stream().collect(Collectors.toMap(BaseWrongQuestions::getTopicId, BaseWrongQuestions::getId));

        //构建考试结果详情集合
        List<BaseExaminationResultDetails> baseExaminationResultDetailsList = vo.getList().stream().map(item -> {
            BaseExaminationResultDetails resultDetails = new BaseExaminationResultDetails();
            resultDetails.setId(IdWorker.getId());
            resultDetails.setCreateTime(LocalDateTime.now());
            resultDetails.setStatus(Constants.STATUS_NORMAL);
            resultDetails.setTestPaperId(vo.getId());
            resultDetails.setTestQuestionsId(item.getId());
            resultDetails.setUpdateTime(LocalDateTime.now());
            resultDetails.setUserId(SecurityUtils.getCurrentUserId());
            resultDetails.setIsCorrect(2);
            resultDetails.setTopicAnswer(item.getAnswer());
            resultDetails.setResultId(resultId);
            //用户选择正确加分
            if(StringUtils.equals(item.getAnswer(),idAnswerMap.get(item.getTopicId()))){
                score[0] += item.getScore();
                resultDetails.setIsCorrect(1);
            }else{
                if(!wrongTopicIList.contains(item.getTopicId())){
                    BaseWrongQuestions questions = BaseWrongQuestions.builder()
                            .id(IdWorker.getId())
                            .userId(SecurityUtils.getCurrentUserId())
                            .courseId(vo.getCourseId())
                            .testPaperId(vo.getId())
                            .topicAnswer(item.getAnswer())
                            .topicId(item.getTopicId())
                            .status(Constants.STATUS_NORMAL)
                            .createTime(LocalDateTime.now())
                            .updateTime(LocalDateTime.now())
                            .type(vo.getType())
                            .build();
                    wrongQuestionsList.add(questions);
                }else{
                    BaseWrongQuestions questions = BaseWrongQuestions.builder()
                            .id(wrongTopicAndIdMapList.get(item.getTopicId()))
                            .updateTime(LocalDateTime.now())
                            .topicAnswer(item.getAnswer())
                            .build();
                    updateWrongQuestionsList.add(questions);
                }
            }
            return resultDetails;
        }).collect(Collectors.toList());

        BaseExaminationResult result = BaseExaminationResult.builder()
                .id(resultId)
                .createTime(LocalDateTime.now())
                .examTime(vo.getEndtime()-vo.getStartTime())
                .status(Constants.STATUS_NORMAL)
                .score(new BigDecimal(score[0]))
                .testPaperId(vo.getId())
                .updateTime(LocalDateTime.now())
                .userId(SecurityUtils.getCurrentUserId())
                .build();
        scheduledConfig.taskExecutor().execute( new SubmitPaperThread(baseExaminationResultService,baseExaminationResultDetailsService,baseWrongQuestionsService,result,baseExaminationResultDetailsList,wrongQuestionsList,updateWrongQuestionsList));

        SubmitPaperRespVo respVo = SubmitPaperRespVo.builder()
                .id(vo.getId())
                .resultId(result.getId())
                .score(score[0])
                .build();
        return respVo;
    }

    @Override
    public Page<WrongQuestionsPageListVo> wrongList(ParamPageVo<WrongQuestionsSeachVo> vo) {
        Page<BaseChapelTest> page = new Page<>(vo.getPage().getCurrent(), vo.getPage().getSize());
        return baseWrongQuestionsService.wrongList(page, vo.getParams(), SecurityUtils.getCurrentUserId());
    }
}
