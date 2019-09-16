package cn.powertime.iatp.facade.web.impl;

import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.BaseKnowledgeInfo;
import cn.powertime.iatp.facade.web.IndexFacade;
import cn.powertime.iatp.service.BaseCourseService;
import cn.powertime.iatp.service.BaseKnowledgeInfoService;
import cn.powertime.iatp.service.BaseUserLearningRecordService;
import cn.powertime.iatp.vo.resp.web.IndexCourseList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class IndexFacadeImpl implements IndexFacade {

    @Autowired
    private BaseKnowledgeInfoService baseKnowledgeInfoService;
    @Autowired
    private BaseCourseService baseCourseService;
    @Autowired
    private BaseUserLearningRecordService baseUserLearningRecordService;


    @Override
    public List<BaseKnowledgeInfo> knowledgeList() {
        return baseKnowledgeInfoService.webIndexKnowledgeList();
    }

    @Override
    public List<IndexCourseList> courseList(Integer type) {
//        List<BaseCourse> list = baseUserLearningRecordService.webIndexCourseList(type);
//        if(!CollectionUtils.isEmpty(list) && list.size() == 8){
//            return list;
//        }else{
//            Integer num = 8 - list.size();
//            List<Long> ids = list.stream().map(BaseCourse::getId).collect(Collectors.toList());
//            List<BaseCourse> lists = baseCourseService.webIndexCourseList(type,num,ids);
//            lists.addAll(list);
//            return lists;
//        }
        return baseCourseService.webIndexCourseList(type);
    }
}
