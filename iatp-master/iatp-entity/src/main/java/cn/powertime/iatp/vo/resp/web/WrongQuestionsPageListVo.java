package cn.powertime.iatp.vo.resp.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class WrongQuestionsPageListVo implements Serializable {

    private Long id;
    private String topicStem;//题干
    private String topicAnswer;//试题答案
    private String optionA;//选项A
    private String optionB;//选项B
    private String optionC;//选项C
    private String optionD;//选项D
    private String topicAnalysis;//试题解析
    private Integer testType;//试题归属的试卷类型 1:课程随堂，2：课程单元，3：课程，4：实验随堂，5：实验单元，6：实验，7：综合
    private Integer scoreValue;//分值

}
