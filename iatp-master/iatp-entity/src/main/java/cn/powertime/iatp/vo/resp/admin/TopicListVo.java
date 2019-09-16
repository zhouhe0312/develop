package cn.powertime.iatp.vo.resp.admin;

import lombok.Data;

import java.io.Serializable;
@Data
public class TopicListVo implements Serializable {

    private Long id;
    /**
     * 试题题干
     */
    private String topicStem;

    private Integer scoreValue;
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

}
