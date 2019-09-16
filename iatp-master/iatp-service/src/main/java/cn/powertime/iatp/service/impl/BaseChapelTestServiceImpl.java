package cn.powertime.iatp.service.impl;

import cn.powertime.iatp.entity.BaseChapelTest;
import cn.powertime.iatp.mapper.BaseChapelTestMapper;
import cn.powertime.iatp.service.BaseChapelTestService;
import cn.powertime.iatp.vo.req.admin.BaseChapelTestSearchVo;
import cn.powertime.iatp.vo.req.web.CourseExamSeachVo;
import cn.powertime.iatp.vo.resp.admin.BaseChapelTestPageListVo;
import cn.powertime.iatp.vo.resp.admin.TopicListVo;
import cn.powertime.iatp.vo.resp.web.CourseExamPageListVo;
import cn.powertime.iatp.vo.resp.web.PaperDetailsVo;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 试卷表 服务实现类
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Service
public class BaseChapelTestServiceImpl extends ServiceImpl<BaseChapelTestMapper, BaseChapelTest> implements BaseChapelTestService {

    @Autowired
    private BaseChapelTestMapper baseChapelTestMapper;

    @Override
    public boolean add(BaseChapelTest baseChapelTest) {
        return save(baseChapelTest);
    }

    @Override
    public boolean edit(BaseChapelTest baseChapelTest) {
        return updateById(baseChapelTest);
    }

    @Override
    public Page<BaseChapelTestPageListVo> selectPage(Page<BaseChapelTest> page, BaseChapelTestSearchVo params) {
        return baseChapelTestMapper.mySelectPage(page,params);
    }

    @Override
    public BaseChapelTest selectById(Long id) {
        return getById(id);
    }

    @Override
    public boolean batchDel(List<String> strings) {
        Integer i = baseChapelTestMapper.batchDel(strings);
        return i > 0;
    }

    @Override
    public Page<CourseExamPageListVo> webCourseExamList(Page<BaseChapelTest> page, CourseExamSeachVo params, Long uid) {
        return baseChapelTestMapper.webCourseExamList(page,params,uid);
    }

    @Override
    public PaperDetailsVo paperResult(Long id, Long uid) {
        return baseChapelTestMapper.paperResult(id,uid);
    }

    @Override
    public List<TopicListVo> getTopicListByChapelId(Long id) {
        return baseChapelTestMapper.getTopicListByChapelId(id);
    }

}
