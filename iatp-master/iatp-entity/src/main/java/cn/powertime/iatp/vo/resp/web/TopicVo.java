package cn.powertime.iatp.vo.resp.web;

import lombok.Data;

import java.io.Serializable;
@Data
public class TopicVo implements Serializable {

    private Long id;


    private Long topicId;
    /**
     * 试题题干
     */
    private String topicStem;

    /**
     * 试题答案
     */
    private String topicAnswer;
    /**
     * 试题分值
     */
    private Integer score;

    /**
     * A选项
     */
    private String optionA;

    /**
     * B选项
     */
    private String optionB;

    /**
     * C选项
     */
    private String optionC;

    /**
     * D选项
     */
    private String optionD;

    /**
     * 试题解析
     */
    private String topicAnalysis;

    private String userAnswer;

    private Integer isCorrect;

}
