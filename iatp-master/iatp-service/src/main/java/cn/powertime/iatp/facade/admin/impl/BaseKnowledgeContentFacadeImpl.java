package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.BaseKnowledgeContent;
import cn.powertime.iatp.entity.BaseKnowledgeInfo;
import cn.powertime.iatp.entity.BaseStandard;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.BaseKnowledgeContentFacade;
import cn.powertime.iatp.logging.EnumLogType;
import cn.powertime.iatp.logging.Logging;
import cn.powertime.iatp.service.BaseKnowledgeContentService;
import cn.powertime.iatp.service.BaseKnowledgeInfoService;
import cn.powertime.iatp.service.BaseStandardService;
import cn.powertime.iatp.utils.excel.EasyExcelUtils;
import cn.powertime.iatp.utils.excel.ExcelListener;
import cn.powertime.iatp.vo.req.admin.BaseContentUploadVo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeContentAddVo;
import cn.powertime.iatp.vo.req.admin.BaseKnowledgeContentEditVo;
import cn.powertime.iatp.vo.resp.admin.CommonTree;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 知识内容表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
@Order(2)
public class BaseKnowledgeContentFacadeImpl implements BaseKnowledgeContentFacade {

    @Autowired
    private BaseKnowledgeInfoService baseKnowledgeInfoService;

    @Autowired
    private BaseKnowledgeContentService baseKnowledgeContentService;

    @Autowired
    private BaseStandardService baseStandardService;

    @Override
    public boolean checkNameOnly(Long father, String name, Long id) {
        QueryWrapper<BaseKnowledgeContent> wrapper = new QueryWrapper<>();
        wrapper.eq("father", father);
        wrapper.eq("name", name);
        if (null != id) {
            wrapper.ne("id", id);
        }
        return baseKnowledgeContentService.count(wrapper) > 0;
    }

    @Override
    @Logging(code = "knowledgeContent.add",vars = {"vo.name","vo"},type = EnumLogType.ADD)
    public boolean add(BaseKnowledgeContentAddVo vo) {
        boolean b = checkNameOnly(vo.getFather(), vo.getName(), null);
        if(b){
            throw new IatpException("名称已经存在");
        }
        BaseKnowledgeContent content = new BaseKnowledgeContent();
        BeanUtils.copyProperties(vo, content);
        content.setId(IdWorker.getId());
        return baseKnowledgeContentService.add(content);
    }

    @Override
    @Logging(code = "knowledgeContent.edit",vars = {"vo.name","vo"},type = EnumLogType.UPDATE)
    public boolean edit(BaseKnowledgeContentEditVo vo) {
        BaseKnowledgeContent info = baseKnowledgeContentService.getById(vo.getId());
        if(info != null){
            boolean b = checkNameOnly(info.getFather(), vo.getName(), vo.getId());
            if(b){
                throw new IatpException("名称已经存在");
            }
        }
        BaseKnowledgeContent content = new BaseKnowledgeContent();
        BeanUtils.copyProperties(vo, content);
        return baseKnowledgeContentService.edit(content);
    }

    @Override
    public BaseKnowledgeContent selectById(Long id) {
        return baseKnowledgeContentService.selectById(id);
    }

    @Override
    public List<BaseKnowledgeContent> selectByPid(Long pid) {
        QueryWrapper<BaseKnowledgeContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("father", pid);
        List<BaseKnowledgeContent> list = baseKnowledgeContentService.list(queryWrapper);
        return list;
    }

    @Override
    @Logging(code = "knowledgeContent.del",vars = {"","strings"},type = EnumLogType.DEL)
    public boolean batchDel(List<String> strings) {
        QueryWrapper<BaseKnowledgeContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("father", strings);
        Integer count = baseKnowledgeContentService.count(queryWrapper);
        if (count > 0) {
            throw new IatpException("选中的知识库内容有子节点不能删除");
        }
        QueryWrapper<BaseStandard> queryWrapperStandard = new QueryWrapper<>();
        queryWrapperStandard.in("control_id", strings);
        Integer countStandard = baseStandardService.count(queryWrapperStandard);
        if (countStandard > 0) {
            throw new IatpException("选中的知识库内容有引用关系不能删除");
        }
        return baseKnowledgeContentService.batchDel(strings);
    }

    @Override
    public List<CommonTree> tree(String name,Long pid) {
        CommonTree root = new CommonTree();
        root.setId(0L);
        root.setPid(-1L);
        root.setLabel("知识库");

        List<CommonTree> treeList = new ArrayList<>();
        List<BaseKnowledgeInfo> infoList;
        List<BaseKnowledgeContent> contentList;
        QueryWrapper<BaseKnowledgeInfo> infoWrapper = new QueryWrapper<>();
        QueryWrapper<BaseKnowledgeContent> contentWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            infoWrapper.ne("status",0).ne("status",-1);
            infoWrapper.like("name",name);
            infoWrapper.orderByAsc("id");
            infoList = baseKnowledgeInfoService.list(infoWrapper);
            if(CollectionUtils.isNotEmpty(infoList)){
                root.setChildren(convertTreesInfo(infoList));
            }
        }
        if(pid != null){
            if(pid == 0){
                infoWrapper.ne("status",0).ne("status",-1);
                infoWrapper.orderByAsc("id");
                infoList = baseKnowledgeInfoService.list(infoWrapper);
                if(CollectionUtils.isNotEmpty(infoList)){
                    root.setChildren(convertTreesInfo(infoList));
                }
            }else{
                contentWrapper.eq("father",pid);
                contentWrapper.orderByAsc("id");
                contentList = baseKnowledgeContentService.list(contentWrapper);
                if(CollectionUtils.isNotEmpty(contentList)){
                    treeList = convertTreesContent(contentList);
                }
            }
        }
        if(pid == null ||  pid == 0){
            treeList.add(root);
        }
        return treeList;
    }

    @Override
    public boolean uploadPicture(Long id, String filename) {
        BaseKnowledgeContent content = baseKnowledgeContentService.selectById(id);
        content.setFilename(filename);
        return baseKnowledgeContentService.edit(content);
    }

    @Override
    public boolean uploadContent(MultipartFile file, Long pid) {
        List<BaseContentUploadVo> result;
        try {
            ExcelListener<BaseContentUploadVo> excelListener = new ExcelListener();
            ExcelReader reader = EasyExcelUtils.getReader(file, excelListener);
            reader.read(new Sheet(1, 1, BaseContentUploadVo.class));
            result = excelListener.getDatas();
            if (CollectionUtils.isNotEmpty(result)) {
                return baseKnowledgeContentService.uploadContent(result, pid);
            } else {
                throw new IatpException("读取解析表格数据失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IatpException(e.getMessage());
        }
    }

    private List<CommonTree> convertTreesInfo(List<BaseKnowledgeInfo> list) {
        List<CommonTree> treeList = list.stream().map(item -> {
            CommonTree tree = new CommonTree();
            tree.setId(item.getId());
            tree.setPid(0L);
            tree.setLabel(item.getName());
            return tree;
        }).collect(Collectors.toList());
        return treeList;
    }

    private List<CommonTree> convertTreesContent(List<BaseKnowledgeContent> list) {
        List<CommonTree> treeList = list.stream().map(item -> {
            CommonTree tree = new CommonTree();
            tree.setId(item.getId());
            tree.setPid(Long.valueOf(item.getFather()));
            tree.setLabel(item.getName());
            return tree;
        }).collect(Collectors.toList());
        return treeList;
    }

}
