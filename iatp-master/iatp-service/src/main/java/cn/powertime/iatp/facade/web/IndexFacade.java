package cn.powertime.iatp.facade.web;

import cn.powertime.iatp.entity.BaseKnowledgeInfo;
import cn.powertime.iatp.vo.resp.web.IndexCourseList;

import java.util.List;

public interface IndexFacade {
    List<BaseKnowledgeInfo> knowledgeList();

    List<IndexCourseList> courseList(Integer type);
}
