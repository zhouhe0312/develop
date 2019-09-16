package cn.powertime.iatp.vo.resp.web;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CourseResourceListVo implements Serializable {

    private Long id;
    private String name;
    private Long pid;
    private Long fileId;
    /**
     * 1:小节
     * 2：单元测
     * 3：资源
     */
    private Integer type;
    /**
     * 0：小节或者单元测
     * 1：视频
     * 2：ppt
     * 3：pdf
     * 4：随堂测
     */
    private Integer resourceType;
    private BigDecimal sort;

    private Long resultId;

    private String fileExtensions;

    private Integer defaults;

    private List<CourseResourceListVo> child;

}
