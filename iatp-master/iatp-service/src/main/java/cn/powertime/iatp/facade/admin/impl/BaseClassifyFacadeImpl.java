package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.BaseClassify;
import cn.powertime.iatp.entity.BaseCourse;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.BaseClassifyFacade;
import cn.powertime.iatp.logging.EnumLogType;
import cn.powertime.iatp.logging.Logging;
import cn.powertime.iatp.service.BaseClassifyService;
import cn.powertime.iatp.service.BaseCourseService;
import cn.powertime.iatp.vo.req.admin.BaseClassifyAddVo;
import cn.powertime.iatp.vo.req.admin.BaseClassifyEditVo;
import cn.powertime.iatp.vo.req.admin.BaseClassifySearchVo;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.resp.admin.BaseClassifyPageListVo;
import cn.powertime.iatp.vo.resp.admin.CommonTree;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * 分类表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
@Order(2)
public class BaseClassifyFacadeImpl implements BaseClassifyFacade {

    @Autowired
    private BaseClassifyService baseClassifyService;

    @Autowired
    private BaseCourseService baseCourseService;

    @Override
    public boolean checkNameOnly(Long pid, int type, String name, Long id) {
        QueryWrapper<BaseClassify> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", pid);
        wrapper.eq("type", type);
        wrapper.eq("name", name);
        wrapper.ne("status", Constants.STATUS_DEL);
        if (null != id) {
            wrapper.ne("id", id);
        }
        return baseClassifyService.count(wrapper) > 0;
    }

    @Override
    @Logging(code = "classify.add", vars = {"vo.name", "vo"}, type = EnumLogType.ADD)
    public boolean add(BaseClassifyAddVo vo) {
        boolean b = checkNameOnly(vo.getPid(), vo.getType(), vo.getName(), null);
        if (b) {
            throw new IatpException("分类名称已经存在");
        }
        BaseClassify classify = new BaseClassify();
        BeanUtils.copyProperties(vo, classify);
        classify.setStatus(Constants.STATUS_NORMAL);
        classify.setCreateTime(LocalDateTime.now());
        classify.setUpdateTime(LocalDateTime.now());
        classify.setId(IdWorker.getId());
        return baseClassifyService.add(classify);
    }

    @Override
    @Logging(code = "classify.edit", vars = {"vo.name", "vo"}, type = EnumLogType.UPDATE)
    public boolean edit(BaseClassifyEditVo vo) {
        boolean b = checkNameOnly(vo.getPid(), vo.getType(), vo.getName(), vo.getId());
        if (b) {
            throw new IatpException("分类名称已经存在");
        }
        BaseClassify classify = new BaseClassify();
        BeanUtils.copyProperties(vo, classify);
        classify.setUpdateTime(LocalDateTime.now());
        return baseClassifyService.edit(classify);
    }

    @Override
    public Page<BaseClassifyPageListVo> list(ParamPageVo<BaseClassifySearchVo> vo) {
        Page<BaseClassifyPageListVo> page = new Page<>(vo.getPage().getCurrent(), vo.getPage().getSize());
        return baseClassifyService.selectPage(page, vo.getParams());
    }

    @Override
    public List<BaseClassify> listAll(int type) {
        QueryWrapper<BaseClassify> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constants.STATUS_NORMAL);
        if (0 != type) {
            queryWrapper.eq("type", type);
        }
        return baseClassifyService.list(queryWrapper);
    }

    @Override
    public BaseClassify selectById(Long id) {
        return baseClassifyService.selectById(id);
    }

    @Override
    @Logging(code = "classify.del", vars = {"", "strings"}, type = EnumLogType.DEL)
    public boolean batchDel(List<String> strings) {
        QueryWrapper<BaseClassify> queryClassify = new QueryWrapper<>();
        queryClassify.eq("status", Constants.STATUS_NORMAL);
        queryClassify.in("pid", strings);
        Integer classifyCount = baseClassifyService.count(queryClassify);
        if (classifyCount > 0) {
            throw new IatpException("选中的分类有子分类不能删除");
        }

        QueryWrapper<BaseCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constants.STATUS_NORMAL);
        queryWrapper.in("classify_id", strings);
        Integer count = baseCourseService.count(queryWrapper);
        if (count > 0) {
            throw new IatpException("选中的分类有被课程引用不能删除");
        }
        return baseClassifyService.batchDel(strings);
    }

    @Override
    public boolean enableDisabled(Long id) {
        BaseClassify classify = baseClassifyService.selectById(id);
        if (classify.getStatus().equals(Constants.STATUS_NORMAL)) {
            QueryWrapper<BaseCourse> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("classify_id", id);
            Integer count = baseCourseService.count(queryWrapper);
            if (count > 0) {
                throw new IatpException("选中的分类有被课程引用不能删除");
            }
            classify.setStatus(Constants.STATUS_DISABLED);
        } else if (classify.getStatus().equals(Constants.STATUS_DISABLED)) {
            classify.setStatus(Constants.STATUS_NORMAL);
        }
        return baseClassifyService.updateById(classify);
    }

    @Override
    public List<CommonTree> tree() {
        QueryWrapper<BaseClassify> wrapper = new QueryWrapper<>();
        wrapper.eq("status", Constants.STATUS_NORMAL);
        List<BaseClassify> list = baseClassifyService.list(wrapper);
        CommonTree root = new CommonTree();
        root.setId(0L);
        root.setPid(-1L);
        root.setLabel("根分类");
        if (CollectionUtils.isNotEmpty(list)) {
            root.setChildren(getTrees(list));
        }
        List<CommonTree> treeList = new ArrayList<>();
        treeList.add(root);
        return treeList;
    }

    private List<CommonTree> getTrees(List<BaseClassify> list) {
        List<CommonTree> rootList = list.stream().filter(item -> StringUtils.equals("0", item.getPid() + "")).map(item -> {
            CommonTree tree = new CommonTree();
            tree.setId(item.getId());
            tree.setPid(item.getPid());
            tree.setLabel(item.getName());
            return tree;
        }).collect(Collectors.toList());

        for (CommonTree root : rootList) {
            getChilds(list, root);
        }
        return rootList;
    }

    private void getChilds(List<BaseClassify> childList, CommonTree parentDept) {

        List<CommonTree> cList = childList.stream()
                .filter(item -> StringUtils.equals(item.getPid() + "", parentDept.getId() + ""))
                .map(item -> {
                    CommonTree cTree = new CommonTree();
                    cTree.setId(item.getId());
                    cTree.setPid(item.getPid());
                    cTree.setLabel(item.getName());
                    return cTree;
                })
                .collect(Collectors.toList());
        if (cList == null || cList.isEmpty()) {
            return;
        }
        parentDept.setChildren(cList);
        for (CommonTree pOrg : cList) {
            getChilds(childList, pOrg);
        }

    }
}
