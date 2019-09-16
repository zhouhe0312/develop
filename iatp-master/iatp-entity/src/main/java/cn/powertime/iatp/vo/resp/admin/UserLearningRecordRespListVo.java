package cn.powertime.iatp.vo.resp.admin;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ZYW
 */
@Data
public class UserLearningRecordRespListVo implements Serializable {
    /**
     * 学习ID
     */
    private Long id;
    /**
     * 用户帐号
     */
    private String acount;
    /**
     * 用户姓名
     */
    private String username;
    /**
     * 课程类型（1，课程，2,实验）
     */
    private Integer courseType;
    /**
     * 课程实验名称
     */
    private String courseName;
    /**
     * 章名称
     */
    private String chapterName;
    /**
     * 节名称
     */
    private String sectionName;
    /**
     * 资源名称
     */
    private String resourceName;
    /**
     * 学习进度 单位：秒（视频），页数（pdf/ppt）
     */
    private Long schedule;
    /**
     * 资源大小 单位：秒（视频），页数（pdf/ppt）
     */
    private Long resourceSize;
    /**
     * 状态 学习状态，1 正在学习，2：学习完成
     */
    private Integer type;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
