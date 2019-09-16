package cn.powertime.iatp.vo.resp.web;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class PaperDetailsVo implements Serializable {

    private Long id;
    /**
     * 试卷名称
     */
    private String title;

    /**
     * 考试时长
     */
    private Long paperDuration;

    /**
     * 总分数
     */
    private Integer score;
    /**
     * 用户得分
     */
    private Integer userScore;
    /**
     * 课程ID
     */
    private Long courseId;

    private Integer type;

    private List<TopicVo> topicList;


}
