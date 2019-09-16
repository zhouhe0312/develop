package cn.powertime.iatp.facade.web;

import cn.powertime.iatp.entity.BaseKnowledgeContent;
import cn.powertime.iatp.vo.resp.admin.CommonTree;

import java.util.List;

public interface KnowledgeContentFacade {

    /**
     * 通过pid查询知识库内容tree
     *
     * @param pid
     * @return
     */
    List<CommonTree> tree(Long pid);

    /**
     * 通过pid查询知识库内容列表
     *
     * @param pid
     * @return
     */
    List<BaseKnowledgeContent> listAll(Long pid);

}
