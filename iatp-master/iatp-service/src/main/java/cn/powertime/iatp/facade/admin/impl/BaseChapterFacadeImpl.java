package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.BaseChapelTest;
import cn.powertime.iatp.entity.BaseChapter;
import cn.powertime.iatp.entity.BaseResource;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.BaseChapterFacade;
import cn.powertime.iatp.logging.EnumLogType;
import cn.powertime.iatp.logging.Logging;
import cn.powertime.iatp.service.BaseChapelTestService;
import cn.powertime.iatp.service.BaseChapterService;
import cn.powertime.iatp.service.BaseResourceService;
import cn.powertime.iatp.utils.CollectionUtils;
import cn.powertime.iatp.vo.req.admin.BaseChapterAddVo;
import cn.powertime.iatp.vo.req.admin.BaseChapterEditVo;
import cn.powertime.iatp.vo.resp.admin.CommonTree;
import cn.powertime.iatp.vo.resp.admin.KeyValueVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程（实验）章节表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
@Order(2)
public class BaseChapterFacadeImpl implements BaseChapterFacade {

    @Autowired
    private BaseChapterService baseChapterService;

    @Autowired
    private BaseResourceService baseResourceService;

    @Autowired
    private BaseChapelTestService baseChapelTestService;

    @Override
    public boolean checkNameOnly(Long courseId, Long pid, int type, String name, Long id) {
        QueryWrapper<BaseChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("pid", pid);
        wrapper.eq("type", type);
        wrapper.eq("chapter_name", name);
        wrapper.ne("status", Constants.STATUS_DEL);
        if (null != id) {
            wrapper.ne("id", id);
        }
        return baseChapterService.count(wrapper) > 0;
    }

    @Override
    @Logging(code = "chapter.add", vars = {"vo.chapterName", "vo"}, type = EnumLogType.ADD)
    public boolean add(BaseChapterAddVo vo) {
        boolean b = checkNameOnly(vo.getCourseId(), vo.getPid(), vo.getType(), vo.getChapterName(), null);
        if (b) {
            throw new IatpException("名称已经存在");
        }
        BaseChapter chapter = new BaseChapter();
        BeanUtils.copyProperties(vo, chapter);
        chapter.setStatus(Constants.STATUS_NORMAL);
        chapter.setCreateTime(LocalDateTime.now());
        chapter.setUpdateTime(LocalDateTime.now());
        chapter.setId(IdWorker.getId());
        return baseChapterService.add(chapter);
    }

    @Override
    @Logging(code = "chapter.edit", vars = {"vo.chapterName", "vo"}, type = EnumLogType.UPDATE)
    public boolean edit(BaseChapterEditVo vo) {
        boolean b = checkNameOnly(vo.getCourseId(), vo.getPid(), vo.getType(), vo.getChapterName(), vo.getId());
        if (b) {
            throw new IatpException("名称已经存在");
        }
        BaseChapter chapter = new BaseChapter();
        BeanUtils.copyProperties(vo, chapter);
        chapter.setUpdateTime(LocalDateTime.now());
        return baseChapterService.edit(chapter);
    }

    @Override
    public List<KeyValueVo> selectList(Integer type, Long pidOrCourseId) {
        QueryWrapper<BaseChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        if (type != null && type == 1) {
            wrapper.eq("pid", "0").eq("course_id", pidOrCourseId);
        } else if (type != null && type == 2) {
            wrapper.eq("pid", pidOrCourseId);
        }
        wrapper.orderByDesc("create_time");
        List<BaseChapter> courseList = baseChapterService.list(wrapper);
        if (CollectionUtils.isEmpty(courseList)) {
            return Lists.newArrayList();
        }

        return courseList.stream().map(item -> {
            return KeyValueVo.builder()
                    .key(String.valueOf(item.getId()))
                    .value(item.getChapterName())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    @Logging(code = "chapter.del", vars = {"", "strings"}, type = EnumLogType.DEL)
    public boolean batchDel(List<String> strings) {
        QueryWrapper<BaseResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constants.STATUS_NORMAL);
        queryWrapper.in("chapter_id", strings);
        Integer count = baseResourceService.count(queryWrapper);
        if (count > 0) {
            throw new IatpException("选中的章节有被资源引用不能删除");
        }
        QueryWrapper<BaseChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("status", Constants.STATUS_NORMAL);
        chapterWrapper.in("pid", strings);
        Integer chapterCount = baseChapterService.count(chapterWrapper);
        if (chapterCount > 0) {
            throw new IatpException("选中的章节有子章节引用不能删除");
        }
        QueryWrapper<BaseChapelTest> queryWrapperTest = new QueryWrapper<>();
        queryWrapperTest.eq("status", Constants.STATUS_NORMAL);
        queryWrapperTest.in("chapter_id", strings).or().in("subsection", strings);
        Integer countTest = baseChapelTestService.count(queryWrapperTest);
        if (countTest > 0) {
            throw new IatpException("选中的章节有被试卷引用不能删除");
        }
        return baseChapterService.batchDel(strings);
    }

    @Override
    public List<CommonTree> tree(Integer type, Long courseId) {
        QueryWrapper<BaseChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("status", Constants.STATUS_NORMAL);
        wrapper.eq("type", type);
        wrapper.eq("course_id", courseId);
        wrapper.orderByAsc("id");
        List<BaseChapter> list = baseChapterService.list(wrapper);
        CommonTree root = new CommonTree();
        root.setId(0L);
        root.setPid(-1L);
        root.setLabel("根章节");
        if (CollectionUtils.isNotEmpty(list)) {
            root.setChildren(getTrees(list));
        }
        List<CommonTree> treeList = new ArrayList<>();
        treeList.add(root);
        return treeList;
    }

    private List<CommonTree> getTrees(List<BaseChapter> list) {
        List<CommonTree> rootList = list.stream().filter(item -> StringUtils.equals("0", item.getPid() + "")).map(item -> {
            CommonTree tree = new CommonTree();
            tree.setId(item.getId());
            tree.setPid(item.getPid());
            tree.setLabel(item.getChapterName());
            tree.setDesc(item.getDes());
            return tree;
        }).collect(Collectors.toList());

        for (CommonTree root : rootList) {
            getChilds(list, root);
        }
        return rootList;
    }

    private void getChilds(List<BaseChapter> childList, CommonTree parentTree) {

        List<CommonTree> cList = childList.stream()
                .filter(item -> StringUtils.equals(item.getPid() + "", parentTree.getId() + ""))
                .map(item -> {
                    CommonTree cTree = new CommonTree();
                    cTree.setId(item.getId());
                    cTree.setPid(item.getPid());
                    cTree.setLabel(item.getChapterName());
                    cTree.setDesc(item.getDes());
                    return cTree;
                })
                .collect(Collectors.toList());
        if (cList == null || cList.isEmpty()) {
            return;
        }
        parentTree.setChildren(cList);
        for (CommonTree pOrg : cList) {
            getChilds(childList, pOrg);
        }

    }

}
