package cn.powertime.iatp.facade.admin.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.core.annotation.Facade;
import cn.powertime.iatp.entity.BaseExaminationPaper;
import cn.powertime.iatp.entity.BaseTopic;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.facade.admin.BaseTopicFacade;
import cn.powertime.iatp.logging.EnumLogType;
import cn.powertime.iatp.logging.Logging;
import cn.powertime.iatp.service.BaseExaminationPaperService;
import cn.powertime.iatp.service.BaseTopicService;
import cn.powertime.iatp.utils.excel.EasyExcelUtils;
import cn.powertime.iatp.utils.excel.ExcelListener;
import cn.powertime.iatp.vo.req.admin.*;
import cn.powertime.iatp.vo.resp.admin.BaseTopicPageListVo;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 题库表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Slf4j
@Facade
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
@Order(2)
public class BaseTopicFacadeImpl implements BaseTopicFacade {

    @Autowired
    private BaseTopicService baseTopicService;
    @Autowired
    private BaseExaminationPaperService baseExaminationPaperService;

    @Override
    @Logging(code = "topic.add",vars = {"vo.topicStem","vo"},type = EnumLogType.ADD)
    public boolean add(BaseTopicAddVo vo) {
        checked(vo.getTopicAnswer());
        BaseTopic topic = new BaseTopic();
        BeanUtils.copyProperties(vo,topic);
        topic.setStatus(Constants.STATUS_NORMAL);
        topic.setCreateTime(LocalDateTime.now());
        topic.setUpdateTime(LocalDateTime.now());
        topic.setId(IdWorker.getId());
        return baseTopicService.add(topic);
    }

    @Override
    @Logging(code = "topic.edit",vars = {"vo.topicStem","vo"},type = EnumLogType.UPDATE)
    public boolean edit(BaseTopicEditVo vo) {
        checked(vo.getTopicAnswer());
        BaseTopic topic = new BaseTopic();
        BeanUtils.copyProperties(vo,topic);
        topic.setUpdateTime(LocalDateTime.now());
        return baseTopicService.edit(topic);
    }

    private void checked(String answer){
        if(!StringUtils.equals(answer,"A")&&!StringUtils.equals(answer,"B")&&!StringUtils.equals(answer,"C")&&!StringUtils.equals(answer,"D")){
            throw new IatpException("试题答案不能为空");
        }
    }

    @Override
    public Page<BaseTopicPageListVo> list(ParamPageVo<BaseTopicSearchVo> vo) {
        Page<BaseTopicPageListVo> page = new Page<>(vo.getPage().getCurrent(),vo.getPage().getSize());
        return baseTopicService.selectPage(page,vo.getParams());
    }

    @Override
    public List<BaseTopic> listAll(Long typeId) {
        QueryWrapper<BaseTopic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",Constants.STATUS_NORMAL);
        if(null != typeId){
            queryWrapper.eq("type_id",typeId);
        }

        return baseTopicService.list(queryWrapper);
    }

    @Override
    public BaseTopic selectById(Long id) {
        return baseTopicService.selectById(id);
    }

    @Override
    @Logging(code = "topic.del",vars = {"","strings"},type = EnumLogType.DEL)
    public boolean batchDel(List<String> strings) {

        QueryWrapper<BaseExaminationPaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("topic_id",strings);
        queryWrapper.ne("status", Constants.STATUS_DEL);
        Integer count = baseExaminationPaperService.count(queryWrapper);
        if(count > 0){
            throw new IatpException("选中的试题有被试卷引用不能删除");
        }

        return baseTopicService.batchDel(strings);
    }

    @Override
    @Logging(code = "topic.enableDisabled",vars = {"","id"},type = EnumLogType.UPDATE)
    public boolean enableDisabled(String id) {
        BaseTopic topic = baseTopicService.selectById(Long.valueOf(id));
        if(topic.getStatus()== Constants.STATUS_NORMAL ){
            QueryWrapper<BaseExaminationPaper> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("topic_id",id);
            Integer count = baseExaminationPaperService.count(queryWrapper);
            if(count > 0){
                throw new IatpException("选中的试题有被试卷引用不能禁用");
            }
            topic.setStatus(Constants.STATUS_DISABLED);
        }else if(topic.getStatus() == Constants.STATUS_DISABLED){
            topic.setStatus(Constants.STATUS_NORMAL);
        }
        return baseTopicService.updateById(topic);
    }

    @Override
    public boolean  importExcel(MultipartFile file, Long typeId) {
        List<BaseTopicImportVo> result;
        try {
            ExcelListener<BaseTopicImportVo> excelListener = new ExcelListener();
            ExcelReader reader = EasyExcelUtils.getReader(file, excelListener);
            reader.read(new Sheet(1, 1,BaseTopicImportVo.class));
            result = excelListener.getDatas();
            if(CollectionUtils.isNotEmpty(result)){
                return baseTopicService.importExcel(result,typeId);
            }else{
                throw new IatpException("读取解析表格数据失败！");
            }
        } catch (IatpException e) {
            throw new IatpException(e.getMessage());
        }
    }
}
