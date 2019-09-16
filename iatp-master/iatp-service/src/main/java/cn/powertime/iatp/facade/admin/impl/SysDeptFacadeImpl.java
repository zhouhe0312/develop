package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.SysDept;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.SysDeptFacade;
import cn.powertime.iatp.logging.EnumLogType;
import cn.powertime.iatp.logging.Logging;
import cn.powertime.iatp.service.SysDeptService;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.SysDeptAddVo;
import cn.powertime.iatp.vo.req.admin.SysDeptEditVo;
import cn.powertime.iatp.vo.req.admin.SysDeptListVo;
import cn.powertime.iatp.vo.resp.admin.DeptTree;
import cn.powertime.iatp.vo.resp.admin.SysDeptPageListVo;
import cn.powertime.iatp.vo.resp.admin.SysDictVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
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
 * 部门表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-20
 */
@Facade
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
@Order(2)
public class SysDeptFacadeImpl implements SysDeptFacade {

    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public boolean checkNameAndCodeOnly(int type, String value, Long id) {
        QueryWrapper<SysDept> wrapper = new QueryWrapper<>();
        if (type != 1 && type != 2) {
            throw new IatpException("类型输入有误，只能为1或2");
        }
        //1：名称 2：编码
        if (type == 1) {
            wrapper.eq("name", value);
        }
        if (type == 2) {
            wrapper.eq("code", value);
        }
        wrapper.ne("status", Constants.STATUS_DEL);
        if (null != id) {
            wrapper.ne("id", id);
        }
        return sysDeptService.count(wrapper) > 0;
    }

    @Override
    @Logging(code = "dept.add", vars = {"addVo.name", "addVo"}, type = EnumLogType.ADD)
    public boolean add(SysDeptAddVo addVo) {
        boolean name = checkNameAndCodeOnly(1, addVo.getName(), null);
        if (name) {
            throw new IatpException("部门名称已经存在");
        }
        boolean code = checkNameAndCodeOnly(2, addVo.getCode(), null);
        if (code) {
            throw new IatpException("部门编码已经存在");
        }
        SysDept dept = new SysDept();
        BeanUtils.copyProperties(addVo, dept);
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        dept.setId(IdWorker.getId());
        return sysDeptService.ownSave(dept);
    }

    @Override
    @Logging(code = "dept.edit", vars = {"editVo.name", "editVo"}, type = EnumLogType.UPDATE)
    public boolean edit(SysDeptEditVo editVo) {
        boolean name = checkNameAndCodeOnly(1, editVo.getName(), editVo.getId());
        if (name) {
            throw new IatpException("部门名称已经存在");
        }
        boolean code = checkNameAndCodeOnly(2, editVo.getCode(), editVo.getId());
        if (code) {
            throw new IatpException("部门编码已经存在");
        }
        SysDept dept = new SysDept();
        BeanUtils.copyProperties(editVo, dept);
        dept.setUpdateTime(LocalDateTime.now());
        return sysDeptService.ownUpdateById(dept);
    }

    @Override
    public boolean del(Long id) {
        SysDept dept = new SysDept();
        dept.setId(id);
        dept.setStatus(Constants.STATUS_DEL);
        return sysDeptService.del(dept);
    }

    @Override
    public IPage<SysDeptPageListVo> list(ParamPageVo<SysDeptListVo> vo) {
        Page<SysDictVo> page = new Page<>(vo.getPage().getCurrent(), vo.getPage().getSize());
        return sysDeptService.selectPage(page, vo.getParams());
    }


    @Override
    public SysDept selectById(Long id) {
        return sysDeptService.selectById(id);
    }

    @Override
    @Logging(code = "dept.del", vars = {"", "strings"}, type = EnumLogType.DEL)
    public boolean batchDel(List<String> strings) {
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("pid", strings);
        queryWrapper.ne("status", Constants.STATUS_DEL);
        int count = sysDeptService.count(queryWrapper);
        if (count > 0) {
            throw new IatpException("你选中的节点包含子节点，请先删除子节点！");
        }
        return sysDeptService.batchDel(strings);
    }

    @Override
    public List<DeptTree> tree(int type) {
        QueryWrapper<SysDept> wrapper = new QueryWrapper<>();
        if (type != 0 && type !=1) {
            throw new IatpException("参数输入错误");
        }
        if(type == 0){
            wrapper.ne("status", Constants.STATUS_DEL);

        }else if(type == 1){
            wrapper.eq("status", type);
        }
        wrapper.orderByDesc("id");
        List<SysDept> list = sysDeptService.list(wrapper);
//        if(null == list || list.isEmpty()){
//            return null;
//        }
        DeptTree root = new DeptTree();
        root.setId(0L);
        root.setLabel("根分类");
        root.setChildren(getTrees(list));
        List<DeptTree> classifyList = Lists.newArrayList();
        classifyList.add(root);
        return classifyList;
    }

    private List<DeptTree> getTrees(List<SysDept> list) {
        List<DeptTree> rootList = list.stream().filter(item -> StringUtils.equals("0", item.getPid() + "")).map(item -> {
            DeptTree tree = new DeptTree();
            tree.setId(item.getId());
            tree.setLabel(item.getName());
            return tree;
        }).collect(Collectors.toList());

        for (DeptTree root : rootList) {
            getChilds(list, root);
        }
        return rootList;
    }

    private void getChilds(List<SysDept> childList, DeptTree parentDept) {

        List<DeptTree> cList = childList.stream()
                .filter(item -> StringUtils.equals(item.getPid() + "", parentDept.getId() + ""))
                .map(item -> {
                    DeptTree cTree = new DeptTree();
                    cTree.setId(item.getId());
                    cTree.setLabel(item.getName());
                    return cTree;
                })
                .collect(Collectors.toList());
        if (cList == null || cList.isEmpty()) {
            return;
        }
        parentDept.setChildren(cList);
        for (DeptTree pOrg : cList) {
            getChilds(childList, pOrg);
        }

    }

}
