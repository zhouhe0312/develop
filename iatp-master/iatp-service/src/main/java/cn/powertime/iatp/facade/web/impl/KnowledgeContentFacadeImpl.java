package cn.powertime.iatp.facade.web.impl;

import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.BaseKnowledgeContent;
import cn.powertime.iatp.entity.BaseKnowledgeInfo;
import cn.powertime.iatp.facade.web.KnowledgeContentFacade;
import cn.powertime.iatp.service.BaseKnowledgeContentService;
import cn.powertime.iatp.service.BaseKnowledgeInfoService;
import cn.powertime.iatp.vo.resp.admin.CommonTree;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class KnowledgeContentFacadeImpl implements KnowledgeContentFacade {

    @Autowired
    private BaseKnowledgeContentService baseKnowledgeContentService;
    @Autowired
    private BaseKnowledgeInfoService baseKnowledgeInfoService;

    @Override
    public List<CommonTree> tree(Long pid) {
        List<CommonTree> treeList = new ArrayList<>();
        QueryWrapper<BaseKnowledgeInfo> infoWrapper = new QueryWrapper<>();
        if (pid != null) {
            infoWrapper.ne("status", 0).ne("status", -1);
            infoWrapper.eq("id", pid);
            BaseKnowledgeInfo info = baseKnowledgeInfoService.getOne(infoWrapper);
            if (info != null) {
                CommonTree tree = new CommonTree();
                tree.setId(info.getId());
                tree.setPid(0L);
                tree.setLabel(info.getName());
                tree.setChildren(getTrees(pid));
                treeList.add(tree);
            }
        }
        return treeList;
    }

    @Override
    public List<BaseKnowledgeContent> listAll(Long pid) {
        QueryWrapper<BaseKnowledgeContent> contentWrapper = new QueryWrapper<>();
        contentWrapper.eq("father", pid).or().inSql("father", "select id from base_knowledge_content where father ='" + pid + "'");
        List<BaseKnowledgeContent> contentList = baseKnowledgeContentService.list(contentWrapper);
        return contentList;
    }

    private List<CommonTree> getTrees(Long pid) {
        QueryWrapper<BaseKnowledgeContent> contentWrapper = new QueryWrapper<>();
        contentWrapper.eq("father", pid).or().inSql("father", "select id from base_knowledge_content where father ='" + pid + "'");
        List<BaseKnowledgeContent> contentList = baseKnowledgeContentService.list(contentWrapper);
        if (CollectionUtils.isNotEmpty(contentList)) {
            List<CommonTree> childrenList = contentList.stream().filter(item -> pid.longValue() == item.getFather().longValue()).map(item -> {
                CommonTree tree = new CommonTree();
                tree.setId(item.getId());
                tree.setPid(Long.valueOf(item.getFather()));
                tree.setLabel(item.getName());
                return tree;
            }).collect(Collectors.toList());

            for (CommonTree root : childrenList) {
                getChilds(contentList, root);
            }
            return childrenList;
        }
        return null;
    }

    private void getChilds(List<BaseKnowledgeContent> childList, CommonTree parentDept) {
        List<CommonTree> cList = childList.stream().filter(item -> item.getFather().longValue() == parentDept.getId())
                .map(item -> {
                    CommonTree cTree = new CommonTree();
                    cTree.setId(item.getId());
                    cTree.setPid(Long.valueOf(item.getFather()));
                    cTree.setLabel(item.getName());
                    return cTree;
                }).collect(Collectors.toList());
        if (cList == null || cList.isEmpty()) {
            return;
        }
        parentDept.setChildren(cList);
        for (CommonTree pOrg : cList) {
            getChilds(childList, pOrg);
        }
    }

}
