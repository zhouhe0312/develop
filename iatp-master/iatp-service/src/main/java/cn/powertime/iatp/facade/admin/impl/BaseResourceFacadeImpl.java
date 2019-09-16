package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.BaseResource;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.BaseResourceFacade;
import cn.powertime.iatp.service.BaseResourceService;
import cn.powertime.iatp.vo.req.admin.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>
 * 课程（实验）资源表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class BaseResourceFacadeImpl implements BaseResourceFacade {

    @Autowired
    private BaseResourceService baseResourceService;

    @Override
    public boolean checkNameOnly(Long chapterId, int type, String name, Long id) {
        QueryWrapper<BaseResource> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        wrapper.eq("type", type);
        wrapper.eq("resource_name", name);
        wrapper.eq("status", Constants.STATUS_NORMAL);
        if (null != id) {
            wrapper.ne("id", id);
        }
        return baseResourceService.count(wrapper) > 0;
    }

    @Override
    public boolean add(BaseResourceAddVo vo) {
        int resourceType = vo.getResourceType();
        //1：视频，2：ppt，3：pdf
        if (resourceType == 1) {
            String reg = "(mp4|flv)";//mp4|flv|avi|rm|rmvb|wmv|mpg|mov|asf|asx
            Pattern p = Pattern.compile(reg);
            boolean isVideo = p.matcher(vo.getFileExtensions().toLowerCase()).find();
            if (!isVideo) {
                throw new IatpException("上传的文件类型和所选的内容类型不一致！");
            }
        } else if (resourceType == 2) {
            String reg = "(ppt|pptx)";
            Pattern p = Pattern.compile(reg);
            boolean isPPT = p.matcher(vo.getFileExtensions().toLowerCase()).find();
            if (!isPPT) {
                throw new IatpException("上传的文件类型和所选的内容类型不一致！");
            }
        } else if (resourceType == 3) {
            if (!vo.getFileExtensions().toLowerCase().equals("pdf")) {
                throw new IatpException("上传的文件类型和所选的内容类型不一致！");
            }
        }
        boolean b = checkNameOnly(vo.getChapterId(), vo.getType(), vo.getResourceName(), null);
        if (b) {
            throw new IatpException("名称已经存在");
        }
        double maxSort = 0;
        BaseResource baseResource = baseResourceService.getMaxSort();
        if (baseResource != null) {
            maxSort = baseResource.getSort();
        }

        BaseResource resource = new BaseResource();
        BeanUtils.copyProperties(vo, resource);
        resource.setId(IdWorker.getId());
        resource.setStatus(Constants.STATUS_NORMAL);
        resource.setCreateTime(LocalDateTime.now());
        resource.setUpdateTime(LocalDateTime.now());
        resource.setSort(maxSort + 10);
        return baseResourceService.add(resource);
    }

    @Override
    public boolean addTraining(BaseResourceTrainingAddVo vo) {
        boolean b = checkNameOnly(vo.getChapterId(), vo.getType(), vo.getResourceName(), null);
        if (b) {
            throw new IatpException("名称已经存在");
        }
        double maxSort = 0;
        BaseResource baseResource = baseResourceService.getMaxSort();
        if (baseResource != null) {
            maxSort = baseResource.getSort();
        }

        BaseResource resource = new BaseResource();
        BeanUtils.copyProperties(vo, resource);
        resource.setId(IdWorker.getId());
        resource.setStatus(Constants.STATUS_NORMAL);
        resource.setCreateTime(LocalDateTime.now());
        resource.setUpdateTime(LocalDateTime.now());
        resource.setSort(maxSort + 10);
        return baseResourceService.add(resource);
    }

    @Override
    public boolean edit(BaseResourceEditVo vo) {
        boolean b = checkNameOnly(vo.getChapterId(), vo.getType(), vo.getResourceName(), vo.getId());
        if (b) {
            throw new IatpException("名称已经存在");
        }
        BaseResource resource = new BaseResource();
        BeanUtils.copyProperties(vo, resource);
        resource.setUpdateTime(LocalDateTime.now());
        return baseResourceService.edit(resource);
    }

    @Override
    public boolean editTraining(BaseResourceTrainingEditVo vo) {
        boolean b = checkNameOnly(vo.getChapterId(), vo.getType(), vo.getResourceName(), vo.getId());
        if (b) {
            throw new IatpException("名称已经存在");
        }
        BaseResource resource = new BaseResource();
        BeanUtils.copyProperties(vo, resource);
        resource.setUpdateTime(LocalDateTime.now());
        return baseResourceService.edit(resource);
    }

    @Override
    public List<BaseResource> listAll(Long chapterPid, Long chapterId) {
        QueryWrapper<BaseResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Constants.STATUS_NORMAL);
        if (chapterPid != null) {
            queryWrapper.eq("chapter_pid", chapterPid);
        }
        if (chapterId != null) {
            queryWrapper.eq("chapter_id", chapterId);
        }
        queryWrapper.orderByAsc("sort");
        return baseResourceService.list(queryWrapper);
    }

    @Override
    public boolean batchDel(List<String> strings) {
        return baseResourceService.batchDel(strings);
    }

    @Override
    public boolean sort(BaseResourceSortVo vo) {
        double prevSort = 0;
        double nextSort = 0;
        double sort;
        if (vo.getPrevId().longValue() == vo.getNextId().longValue()) {
            throw new IatpException("上一个排序id和下一个排序id不能相同！");
        }
        if (vo.getPrevId().longValue() != 0) {
            BaseResource prevResource = baseResourceService.getById(vo.getPrevId());
            if (prevResource == null) {
                throw new IatpException("上一个排序记录不存在！");
            }
            prevSort = prevResource.getSort();
        }
        if (vo.getNextId().longValue() != 0) {
            BaseResource nextResource = baseResourceService.getById(vo.getNextId());
            if (nextResource == null) {
                throw new IatpException("下一个排序记录不存在！");
            }
            nextSort = nextResource.getSort();
        } else {
            nextSort = prevSort + 10;
        }
        sort = (prevSort + nextSort) / 2;
        BaseResource resource = baseResourceService.getById(vo.getId());
        if (resource == null) {
            throw new IatpException("将要排序的记录不存在！");
        }
        resource.setSort(sort);
        resource.setUpdateTime(LocalDateTime.now());
        return baseResourceService.edit(resource);
    }

}
