package cn.powertime.iatp.vo.resp.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseDetails implements Serializable {

    private Long id;

    /**
     * 课程（实验）名称
     */
    private String courseName;

    /**
     * 课程（实验）介绍
     */
    private String introduce;
    /**
     * 课程（实验）封面
     */
    private Long fileId;

    private Integer chapterCount;

    private Integer studyCount;

    private Integer type;

}
