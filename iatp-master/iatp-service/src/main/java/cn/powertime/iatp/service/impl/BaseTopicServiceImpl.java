package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.commons.Constants;
import cn.powertime.iatp.entity.BaseTopic;
import cn.powertime.iatp.exception.IatpException;
import cn.powertime.iatp.mapper.BaseTopicMapper;
import cn.powertime.iatp.service.BaseTopicService;
import cn.powertime.iatp.vo.req.admin.BaseTopicImportVo;
import cn.powertime.iatp.vo.req.admin.BaseTopicSearchVo;
import cn.powertime.iatp.vo.resp.admin.BaseTopicPageListVo;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class BaseTopicServiceImpl extends ServiceImpl<BaseTopicMapper, BaseTopic> implements BaseTopicService {

    @Autowired
    private BaseTopicMapper baseTopicMapper;


    @Override
    public boolean add(BaseTopic topic) {
        return save(topic);
    }

    @Override
    public boolean edit(BaseTopic topic) {
        return updateById(topic);
    }

    @Override
    public Page<BaseTopicPageListVo> selectPage(Page<BaseTopicPageListVo> page, BaseTopicSearchVo params) {
        return baseTopicMapper.mySelectPage(page,params);
    }

    @Override
    public BaseTopic selectById(Long id) {
        return getById(id);
    }

    @Override
    public boolean batchDel(List<String> strings) {
        Integer i = baseTopicMapper.batchDel(strings);
        return i > 0;
    }

    @Override
    public boolean  importExcel(List<BaseTopicImportVo> result,Long typeId) {
        List<BaseTopic> collect = Lists.newArrayList();
        for(BaseTopicImportVo item : result){

            if(item.isEmpty()){
                continue;
            }

            if (StringUtils.isBlank(item.getOptionA()) ||
                    StringUtils.isBlank(item.getOptionB()) ||
                    StringUtils.isBlank(item.getOptionC()) ||
                    StringUtils.isBlank(item.getOptionD())  ){
                throw new IatpException("上传模板选项中有空数据，请检查。");
            }

            if (item.getOptionA().length() > 500 ||
                    item.getOptionB().length() > 500 ||
                    item.getOptionC().length() > 500 ||
                    item.getOptionD().length() > 500  ){
                throw new IatpException("上传模板选项长度不能超过500，请检查。");
            }
            if(item.getScoreValue() == null){
                throw new IatpException("默认分值不能为空。");
            }
            if(item.getScoreValue() <= 0 || item.getScoreValue() > 100){
                throw new IatpException("试题分值不能小于0，大于100，请检查。");
            }
            if(!StringUtils.equals(item.getTopicAnswer(),"A")&&!StringUtils.equals(item.getTopicAnswer(),"B")&&!StringUtils.equals(item.getTopicAnswer(),"C")&&!StringUtils.equals(item.getTopicAnswer(),"D")){
                throw new IatpException("试题答案只能是A B C D，请检查。");
            }

            if(StringUtils.isBlank(item.getTopicAnalysis())){
                throw new IatpException("试题解析不能为空。");
            }

            BaseTopic topic = new BaseTopic();
            BeanUtils.copyProperties(item, topic);
            topic.setStatus(Constants.STATUS_NORMAL);
            topic.setCreateTime(LocalDateTime.now());
            topic.setUpdateTime(LocalDateTime.now());
            topic.setId(IdWorker.getId());
            topic.setTypeId(typeId);
            collect.add(topic);
        }

        return saveBatch(collect);
    }

}
