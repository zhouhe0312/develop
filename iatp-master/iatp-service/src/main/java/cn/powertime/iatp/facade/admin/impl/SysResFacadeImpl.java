package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.SysRes;
import cn.powertime.iatp.entity.SysRoleRes;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.SysResFacade;
import cn.powertime.iatp.logging.EnumLogType;
import cn.powertime.iatp.logging.Logging;
import cn.powertime.iatp.service.SysResService;
import cn.powertime.iatp.service.SysRoleResService;
import cn.powertime.iatp.vo.req.admin.ParamPageVo;
import cn.powertime.iatp.vo.req.admin.SysMenuListVo;
import cn.powertime.iatp.vo.req.admin.SysResAddVo;
import cn.powertime.iatp.vo.req.admin.SysResEditVo;
import cn.powertime.iatp.vo.resp.admin.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : liqi
 * Date: 2019-04-18
 * Time: 19:02
 */
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
@Facade
@Order(2)
public class SysResFacadeImpl implements SysResFacade {

    @Autowired
    private SysResService sysResService;
    @Autowired
    private SysRoleResService sysRoleResService;

    @Override
    public boolean checkNameAndCodeOnly(int type, String value, Long id) {
        QueryWrapper<SysRes> wrapper = new QueryWrapper<>();
        if (type != 1 && type != 2) {
            throw new IatpException("类型输入有误，只能为1或2");
        }
        //1：名称 2：编码
        if (type == 1) {
            wrapper.eq("name", value);
        }
        if (type == 2) {
            wrapper.eq("permission", value);
        }
        wrapper.ne("status", Constants.STATUS_DEL);
        if (id != null) {
            wrapper.ne("id", id);
        }
        return sysResService.count(wrapper) > 0;
    }

    @Override
    @Logging(code = "res.add",vars = {"addVo.name","addVo"},type = EnumLogType.ADD)
    public boolean add(SysResAddVo addVo) {
        boolean name = checkNameAndCodeOnly(1, addVo.getName(), null);
        if (name) {
            throw new IatpException("名称已经存在");
        }
        boolean code = checkNameAndCodeOnly(2, addVo.getPermission(), null);
        if (code) {
            throw new IatpException("编码已经存在");
        }
        SysRes menu = new SysRes();
        BeanUtils.copyProperties(addVo,menu);
        menu.setId(IdWorker.getId());
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        return sysResService.ownSave(menu);
    }

    @Override
    @Logging(code = "res.edit",vars = {"editVo.name","editVo"},type = EnumLogType.UPDATE)
    public boolean edit(SysResEditVo editVo) {
        boolean name = checkNameAndCodeOnly(1, editVo.getName(), editVo.getId());
        if (name) {
            throw new IatpException("名称已经存在");
        }
        boolean code = checkNameAndCodeOnly(2, editVo.getPermission(), editVo.getId());
        if (code) {
            throw new IatpException("编码已经存在");
        }
        SysRes menu = new SysRes();
        BeanUtils.copyProperties(editVo,menu);
        menu.setUpdateTime(LocalDateTime.now());
        return sysResService.ownUpdateById(menu);
    }

    @Override
    public boolean del(Long id) {
        SysRes menu = new SysRes();
        menu.setId(id);
        menu.setStatus(Constants.STATUS_DEL);
        return sysResService.del(menu);
    }

    @Override
    public SysRes selectById( Long id) {
        return sysResService.getById(id);
    }

    @Override
    public List<MenuTree> tree() {
        List<SysResAndChildCountVo> list = sysResService.tree();
//        if(null == list || list.isEmpty()){
//            return null;
//        }
        MenuTree root = new MenuTree();
        root.setId(0L);
        root.setLabel("智能审计学习平台");
        root.setChildren(getTrees(list));
        List<MenuTree> menuList = Lists.newArrayList();
        menuList.add(root);
        return menuList;
    }

    @Override
    public List<ZtreeVo> ztree(Long roleId) {
        List<SysRes> sysRes = sysResService.selectZtree();
        if(null == sysRes || sysRes.isEmpty()){
            return null;
        }
        Set<Long> resIds = Sets.newHashSet();
        if(null != roleId){
            QueryWrapper<SysRoleRes> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id",roleId);
            List<SysRoleRes> roleResList = sysRoleResService.list(queryWrapper);
            resIds = roleResList.stream().map(SysRoleRes::getResId).collect(Collectors.toSet());
        }
        Set<Long> finalResIds = resIds;
        return sysRes.stream().map(item -> {
            boolean isChecked = false;
            boolean isMenu = false;
            if(finalResIds.contains(item.getId())){
                isChecked = true;
            }
            if(StringUtils.equals(String.valueOf(item.getType()),"1")){
                isMenu = true;
            }
            return new ZtreeVo(item.getId(),item.getPid(),item.getName(),isMenu,isChecked);
        }).collect(Collectors.toList());
    }

    @Override
    public MenuAndButtonVo menuTreeByUserId(Long userId) {

        HashMap<String, ButtonVo> map = Maps.newHashMap();
        List<SysRes>  buttonList= sysResService.buttonsByUserId(userId);
        if (!buttonList.isEmpty()){
            buttonList.forEach(item->{
                ButtonVo vo = new ButtonVo();
                BeanUtils.copyProperties(item,vo);
                vo.setIcon(item.getIcon1());
                map.put(vo.getPermission(),vo);
            });
        }
        List<MenuTreeVo> parentList = null;
        List<SysRes>  menuList= sysResService.listByUserId(userId);
        if (menuList.size()>0){
             parentList = menuList.stream()
                    .filter(item -> 0 == item.getPid())
                    .map(item-> new MenuTreeVo(item.getId(),item.getPid(),item.getName(),item.getPermission(),item.getUrl(),item.getSortNum(),item.getIcon1(),item.getIcon2(),item.getType(),item.getDes()))
                    .sorted(Comparator.comparingInt(item -> Optional.ofNullable(item.getSortNum()).orElse(0)))
                    .collect(Collectors.toList());
            parentList.forEach(item->{
                //封装菜单树
                menuTree(item,menuList);
            });
        }
        MenuAndButtonVo menuAndButtonVo = MenuAndButtonVo.builder()
                .buttons(map)
                .mnues(parentList)
                .build();
        return menuAndButtonVo;
    }

    @Override
    public ElementTreeVo elementTree(Long roleId) {
        ElementTreeVo tree = new ElementTreeVo();
        List<SysResAndChildCountVo> list = sysResService.listSysResAndChildCount();
        if(null == list || list.isEmpty()){
            return null;
        }
        List<MenuTree> treeList  = getTrees(list);
        tree.setTree(treeList);

        Set<Long> resIds = Sets.newHashSet();
        if(null != roleId){
            QueryWrapper<SysRoleRes> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id",roleId);
            List<SysRoleRes> roleResList = sysRoleResService.list(queryWrapper);
            resIds = roleResList.stream().map(SysRoleRes::getResId).collect(Collectors.toSet());
        }
        Set<Long> finalResIds = resIds;
        List<Long> checkedList = Lists.newArrayList();
        list.forEach(item ->{
            if(item.getNum() == 0 && finalResIds.contains(item.getId())){
                checkedList.add(item.getId());
            }
        });
        tree.setCheckedKeys(checkedList);
        return tree;
    }


    @Override
    public Page<SysResPageVo> list(ParamPageVo<SysMenuListVo> vo) {
        Page<SysResPageVo> page = new Page<>(vo.getPage().getCurrent(),vo.getPage().getSize());
        return sysResService.selectPage(page,vo.getParams());
    }

    @Override
    @Logging(code = "res.del",vars = {"","strings"},type = EnumLogType.DEL)
    public boolean batchDel(List<String> strings) {
        QueryWrapper<SysRes> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("pid",strings);
        queryWrapper.ne("status", Constants.STATUS_DEL);
        int count = sysResService.count(queryWrapper);
        if(count > 0){
            throw new IatpException("你选中的节点包含子节点，请先删除子节点！");
        }
        return sysResService.batchDel(strings);
    }

    private List<MenuTree> getTrees(List<SysResAndChildCountVo> list) {
        List<MenuTree> rootList = list.stream().filter(item -> StringUtils.equals("0", item.getPid()+"")).map(item -> {
            MenuTree tree= new MenuTree();
            tree.setId(item.getId());
            tree.setLabel(item.getName());
            tree.setTopId(item.getId());
            tree.setPid(item.getPid());
            tree.setUrl(item.getUrl());
            return tree;
        }).collect(Collectors.toList());

        for(MenuTree root : rootList) {
            getChilds(list,root,root.getId());
        }
        return rootList;
    }

    private void getChilds(List<SysResAndChildCountVo> childList,  MenuTree parentDept,Long topId){

        List<MenuTree> cList = childList.stream()
                .filter(item -> StringUtils.equals(item.getPid()+"", parentDept.getId()+""))
                .map(item -> {
                    MenuTree cTree = new MenuTree();
                    cTree.setId(item.getId());
                    cTree.setTopId(topId);
                    cTree.setLabel(item.getName());
                    cTree.setPid(item.getPid());
                    cTree.setUrl(item.getUrl());
                    return cTree;
                })
                .collect(Collectors.toList());
        if(cList == null || cList.isEmpty()) {
            return;
        }
        parentDept.setChildren(cList);
        for(MenuTree pOrg : cList) {
            getChilds(childList,pOrg,topId);
        }

    }

    public void menuTree(MenuTreeVo pItem, List<SysRes> items) {
        List<MenuTreeVo> menusAndButtons = items.stream()
                .filter(item -> item.getPid().equals(pItem.getId()))
                .map(item -> new MenuTreeVo(item.getId(), item.getPid(), item.getName(), item.getPermission(), item.getUrl(),((SysRes) item).getSortNum(), item.getIcon1(),item.getIcon2(),item.getType(), item.getDes()))
                .collect(Collectors.toList());

        List<MenuTreeVo> menus = menusAndButtons.stream()
                .filter(item -> item.getType() == 1)
                .sorted(Comparator.comparingInt(item -> Optional.ofNullable(item.getSortNum()).orElse(0)))
                .collect(Collectors.toList());
        if(menus == null || menus.isEmpty()) {
            return;
        }
        pItem.setChildrenMenuTree(menus);
        for(MenuTreeVo item : menus) {
            menuTree(item,items);
        }
    }


}
