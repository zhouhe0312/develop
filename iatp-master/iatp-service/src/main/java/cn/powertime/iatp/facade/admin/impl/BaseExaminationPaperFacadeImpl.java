package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.BaseChapelTest;
import cn.powertime.iatp.entity.BaseExaminationPaper;
import cn.powertime.iatp.entity.BaseExaminationResult;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.BaseExaminationPaperFacade;
import cn.powertime.iatp.service.BaseChapelTestService;
import cn.powertime.iatp.service.BaseExaminationPaperService;
import cn.powertime.iatp.service.BaseExaminationResultService;
import cn.powertime.iatp.vo.req.admin.BuildPaperList;
import cn.powertime.iatp.vo.req.admin.SynthesizeBuildVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.common.collect.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 试卷试题表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
public class BaseExaminationPaperFacadeImpl implements BaseExaminationPaperFacade {

    @Autowired
    private BaseExaminationPaperService baseExaminationPaperService;
    @Autowired
    private BaseExaminationResultService baseExaminationResultService;
    @Autowired
    private BaseChapelTestService baseChapelTestService;


    @Override
    public boolean build(BuildPaperList list) {

        //验证当前试卷是否被用户考过
        QueryWrapper<BaseExaminationResult> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("test_paper_id",list.getTestPaperId());
        queryWrapper.eq("status",Constants.STATUS_NORMAL);
        Integer count = baseExaminationResultService.count(queryWrapper);
        if(count > 0){
            throw new IatpException("当前试卷已有考试记录，不能重新组卷！");
        }
        QueryWrapper<BaseExaminationPaper> removeWrapper = new QueryWrapper<>();
        removeWrapper.eq("test_paper_id",list.getTestPaperId());
        baseExaminationPaperService.remove(removeWrapper);

        List<BaseExaminationPaper> papers = list.getList().stream().map(item -> {
            BaseExaminationPaper paper = new BaseExaminationPaper();
            BeanUtils.copyProperties(item, paper);
            paper.setId(IdWorker.getId());
            paper.setTestPaperId(list.getTestPaperId());
            paper.setCreateTime(LocalDateTime.now());
            paper.setUpdateTime(LocalDateTime.now());
            paper.setStatus(Constants.STATUS_NORMAL);
            return paper;
        }).collect(Collectors.toList());
        return baseExaminationPaperService.build(papers);
    }

    @Override
    public boolean synthesizeBuild(SynthesizeBuildVo vo) {
        //验证当前试卷是否被用户考过
        QueryWrapper<BaseExaminationResult> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("test_paper_id",vo.getChapelTestId());
        queryWrapper.eq("status",Constants.STATUS_NORMAL);
        Integer count = baseExaminationResultService.count(queryWrapper);
        if(count > 0){
            throw new IatpException("当前试卷已有考试记录，不能重新组卷！");
        }
        QueryWrapper<BaseExaminationPaper> removeWrapper = new QueryWrapper<>();
        removeWrapper.eq("test_paper_id",vo.getChapelTestId());
        baseExaminationPaperService.remove(removeWrapper);

        Set<Long> set = Sets.newHashSet();
        List<Long> reverseList = Lists.newArrayList();
        BaseChapelTest baseChapelTest = baseChapelTestService.selectById(vo.getChapelTestId());
        List<Long> topicIds = baseExaminationPaperService.getTopicsByCouseId(baseChapelTest.getCourseOneId());
        if(topicIds.size() > 70){
            set.addAll(subCollection(topicIds,70,true));
            reverseList.addAll(subCollection(topicIds,topicIds.size()-70,false));
        }else {
            set.addAll(topicIds);
        }
        //查询实验考题
        if(null != vo.getCourseId() && 0 != vo.getCourseId()){
            List<Long> topicList = baseExaminationPaperService.getTopicsByCouseId(vo.getCourseId());
            Integer size = 100 - set.size();
            if(topicList.size() > size){
                set.addAll(subCollection(topicList,size,true));
            }else {
                set.addAll(topicList);
            }
            baseChapelTest.setCourseTwoId(vo.getCourseId());
        }
        if(set.size() < 100){
            Integer size = 100 - set.size();
            if(reverseList.size() > size){
                set.addAll(subCollection(reverseList,size,true));
            }else{
                set.addAll(reverseList);
            }

        }
        if(set.size() == 0){
            throw new IatpException("当前试卷对应的课程和实验没有相关试题！");
        }

        LinkedList<Integer> scoreList = scoreList(set.size());

        List<BaseExaminationPaper> papers = set.stream().map(item -> {
            BaseExaminationPaper paper = new BaseExaminationPaper();
            paper.setId(IdWorker.getId());
            paper.setTestPaperId(vo.getChapelTestId());
            paper.setScoreValue(scoreList.poll());
            paper.setTopicId(item);
            paper.setCreateTime(LocalDateTime.now());
            paper.setUpdateTime(LocalDateTime.now());
            paper.setStatus(Constants.STATUS_NORMAL);
            return paper;
        }).collect(Collectors.toList());
         boolean b = baseExaminationPaperService.build(papers);
         if(!b){
             throw new IatpException("组卷失败");
         }
        return baseChapelTestService.edit(baseChapelTest);
    }

    @Override
    public boolean isTestAssembly(Long testPaperId) {
        QueryWrapper<BaseExaminationPaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("test_paper_id",testPaperId);
        queryWrapper.eq("status",Constants.STATUS_NORMAL);
        int count = baseExaminationPaperService.count(queryWrapper);
        return count > 0;
    }

    private static Collection subCollection(Collection obj, int size, boolean isAes) {
        if (CollectionUtils.isEmpty(obj)) {
            return Collections.emptySet();
        }
        if(!isAes){
            Collections.reverse((List<Long>) obj );
            return ImmutableList.copyOf(Iterables.limit(obj, size));
        }else{
            Collections.shuffle((List<Long>)obj);
        }
        return ImmutableSet.copyOf(Iterables.limit(obj, size));
    }

    private static LinkedList<Integer> scoreList(Integer num){
        LinkedList<Integer> list = Lists.newLinkedList();
        Integer i = 100 % num;
        if(i == 0){
            for (int j = 0 ;j < num; j++){
                list.add(100 / num);
            }
        }else {
            Integer k = 100 / num;
            for (int j = 0 ;j < num; j++){
                if(j < i){
                    list.add(k+1);
                }else{
                    list.add(k);
                }

            }
        }
        return list;
    }

}
