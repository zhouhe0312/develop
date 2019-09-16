package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.BaseKnowledgeContent;
import cn.powertime.iatp.entity.BaseStandard;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.mapper.BaseKnowledgeContentMapper;
import cn.powertime.iatp.mapper.BaseStandardMapper;
import cn.powertime.iatp.service.BaseKnowledgeContentService;
import cn.powertime.iatp.vo.req.admin.BaseContentUploadVo;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 知识内容表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Service
public class BaseKnowledgeContentServiceImpl extends ServiceImpl<BaseKnowledgeContentMapper, BaseKnowledgeContent> implements BaseKnowledgeContentService {

    @Autowired
    private BaseKnowledgeContentMapper baseKnowledgeContentMapper;

    @Autowired
    private BaseStandardMapper baseStandardMapper;

    @Override
    public boolean add(BaseKnowledgeContent content) {
        return save(content);
    }

    @Override
    public boolean edit(BaseKnowledgeContent content) {
        return updateById(content);
    }

    @Override
    public BaseKnowledgeContent selectById(Long id) {
        return getById(id);
    }

    @Override
    public boolean batchDel(List<String> strings) {
        Integer i = baseKnowledgeContentMapper.batchDel(strings);
        return i > 0;
    }

    @Override
    public boolean uploadContent(List<BaseContentUploadVo> result, Long pid) {
        HashMap<String, Long> map = Maps.newHashMap();
        HashMap<String, Long> mapControl = Maps.newHashMap();
        HashMap<String, String> mapDemain = Maps.newHashMap();
        List<BaseKnowledgeContent> list;
        List<BaseStandard> listStandard;
        Set<String> names = new HashSet();
        list = result.stream().filter(item -> StringUtils.isNotEmpty(item.getName())).map(item -> {
            if(!names.add(item.getName().trim())){
                throw new IatpException("章节“"+item.getName()+"”在模板中有重复记录");
            }
            if (item.getName().length() > 50) {
                throw new IatpException("上传的Excel文件中列名“章节”不能超过50个字符，请检查。");
            }

            if (StringUtils.isNotEmpty(item.getContent()) && item.getContent().length() > 300) {
                throw new IatpException("上传的Excel文件中列名“控制措施要求（审计依据原文）”不能超过300个字符，请检查。");
            }


            BaseKnowledgeContent content = new BaseKnowledgeContent();
            content.setId(IdWorker.getId());
            content.setName(item.getName() != null ? item.getName().trim() : null);
            if (StringUtils.isEmpty(item.getNo())) {
                content.setFather(pid);
                content.setIdx(1f);
            } else {
                map.put(item.getNo(), content.getId());
                List<String> strs = Splitter.on(".").splitToList(item.getNo());
                if (CollectionUtils.isNotEmpty(strs)) {
                    if (strs.size() == 1) {
                        content.setFather(pid);
                    } else {
                        content.setFather(map.get(item.getNo().substring(0, item.getNo().lastIndexOf("."))));
                    }
                    content.setIdx(Float.valueOf(item.getNo().substring(item.getNo().lastIndexOf(".") + 1)));
                }
            }
            content.setContent(item.getContent() != null ? item.getContent().trim() : null);
            content.setRootId(pid);
            return content;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(list)) {
            throw new IatpException("上传的Excel文件中章节列数据为空。");
        }

        listStandard = result.stream().filter(item -> StringUtils.isNotEmpty(item.getQuestion())).map(item -> {
            if (item.getQuestion().length() > 200) {
                throw new IatpException("上传的Excel文件中列名“审计评价标准及方法”字符长度不能超过200个字符，请检查。");
            }

            if (item.getType() == null) {
                throw new IatpException("上传的Excel文件中列名“类型”有空数据，请检查。");
            }
            if (!item.getType().equals("管理") && !item.getType().equals("技术")) {
                throw new IatpException("上传的Excel文件中列名“类型”只能为管理或技术，请检查。");
            }

            if (StringUtils.isEmpty(item.getNote())) {
                throw new IatpException("上传的Excel文件中列名“收集审计证据”有空数据，请检查。");
            }
            if (item.getNote().length() > 200) {
                throw new IatpException("上传的Excel文件中列名“收集审计证据”字符长度不能超过200个字符，请检查。");
            }


            if (StringUtils.isEmpty(item.getProblem())) {
                throw new IatpException("上传的Excel文件中列名“审计发现问题列表”有空数据，请检查。");
            }
            if (item.getProblem().length() > 200) {
                throw new IatpException("上传的Excel文件中列名“审计发现问题列表”字符长度不能超过200个字符，请检查。");
            }

            if (StringUtils.isEmpty(item.getRish())) {
                throw new IatpException("上传的Excel文件中列名“风险描述”有空数据，请检查。");
            }
            if (item.getRish().length() > 200) {
                throw new IatpException("上传的Excel文件中列名“风险描述”字符长度不能超过200个字符，请检查。");
            }

            if (item.getRishLevel() == null) {
                throw new IatpException("上传的Excel文件中列名“风险等级”有空数据，请检查。");
            }
            if (!item.getRishLevel().equals("高") && !item.getRishLevel().equals("中") && !item.getRishLevel().equals("低")) {
                throw new IatpException("上传的Excel文件中列名“风险等级”只能为高/中/低，请检查。");
            }

            if (StringUtils.isEmpty(item.getSuggest())) {
                throw new IatpException("上传的Excel文件中列名“建议”有空数据，请检查。");
            }
            if (item.getSuggest().length() > 200) {
                throw new IatpException("上传的Excel文件中列名“建议”字符长度不能超过200个字符，请检查。");
            }

            if (StringUtils.isEmpty(item.getAuditUnit())) {
                throw new IatpException("上传的Excel文件中列名“控制措施责任主体（审计对象）”有空数据，请检查。");
            }
            if (item.getAuditUnit().length() > 20) {
                throw new IatpException("上传的Excel文件中列名“控制措施责任主体（审计对象）”字符长度不能超过20个字符，请检查。");
            }


            BaseStandard standard = new BaseStandard();
            if (StringUtils.isNotEmpty(item.getNo())) {
                standard.setControlId(map.get(item.getNo()));
                mapControl.put("controlId", standard.getControlId());
            } else {
                standard.setControlId(mapControl.get("controlId"));
            }
            if (StringUtils.isNotEmpty(item.getContent())) {
                standard.setDemain(item.getContent().trim());
                mapDemain.put("demain", standard.getDemain());
            } else {
                standard.setDemain(mapDemain.get("demain"));
            }
            standard.setId(IdWorker.getId());
            standard.setQuestion(item.getQuestion() != null ? item.getQuestion().trim() : null);
            standard.setNote(item.getNote() != null ? item.getNote().trim() : null);
            standard.setProblem(item.getProblem() != null ? item.getProblem().trim() : null);
            standard.setAnwserRoler(item.getAnwserRoler());
            if (StringUtils.isNotEmpty(item.getRishLevel())) {
                if (item.getRishLevel().trim().equals("低")) {
                    standard.setRishLevel(1);
                } else if (item.getRishLevel().trim().equals("中")) {
                    standard.setRishLevel(2);
                } else if (item.getRishLevel().trim().equals("高")) {
                    standard.setRishLevel(3);
                } else {
                    standard.setRishLevel(0);
                }
            }
            standard.setRish(item.getRish() != null ? item.getRish().trim() : null);
            standard.setSuggest(item.getSuggest() != null ? item.getSuggest().trim() : null);
            if (StringUtils.isNotEmpty(item.getType())) {
                if (item.getType().trim().equals("管理")) {
                    standard.setType(0);
                } else if (item.getType().trim().equals("技术")) {
                    standard.setType(1);
                }
            }
            standard.setAuditUnit(item.getAuditUnit() != null ? item.getAuditUnit().trim() : null);
            //standard.setDepend(item.getDepend() != null ? item.getDepend().trim() : null);
            return standard;
        }).collect(Collectors.toList());
        listStandard.forEach(baseStandardMapper::insert);
        return saveBatch(list);
    }

}
