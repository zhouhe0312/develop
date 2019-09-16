package cn.powertime.iatp.vo.req.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description="课程/实验 考核搜索对象")
public class CourseExamSeachVo implements Serializable {
    @ApiModelProperty(value="试卷名称",name="title")
    private String title;

    /**
     * 1:课程随堂，2：课程单元，3：课程，4：实验随堂，5：实验单元，6：实验，7：综合
     */
    @ApiModelProperty(value="试题类型 1:课程随堂，2：课程单元，3：课程，4：实验随堂，5：实验单元，6：实验，7：综合",name="type")
    private Integer type;
    /**
     * 1:已答试卷
     * 2：已收藏试卷
     */
    @ApiModelProperty(value="搜索类型 1:已答试卷，2：已收藏试卷",name="searchType")
    private Integer searchType;


    @ApiModelProperty(value="试卷大类 0：我的题库 1:课程，2：试卷，3：综合",name="testType")
    private Integer testType;

}
