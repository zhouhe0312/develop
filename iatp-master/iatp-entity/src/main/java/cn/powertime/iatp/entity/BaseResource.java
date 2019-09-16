package cn.powertime.iatp.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 课程（实验）资源表
 * </p>
 *
 * @author liqi
 * @since 2019-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 类型，1 课程，2：实验
     */
    private Integer type;

    /**
     * 是否默认打开 1：是，2：否
     */
    private Integer defaults;

    /**
     * 课程（实验）ID
     */
    private Long courseId;

    /**
     * 章节ID
     */
    private Long chapterPid;

    /**
     * 小节ID
     */
    private Long chapterId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源介绍
     */
    private String introduce;

    /**
     * 资源类型，1：视频，2：ppt，3：pdf，4:课程，5：实验，6：实训
     */
    private Integer resourceType;

    /**
     * 资源文件
     */
    private Long fileId;

    /**
     * 资源文件扩展名
     */
    private String fileExtensions;

    /**
     * 资源大小 单位：秒（视频），页数（pdf/ppt）
     */
    private Long resourceSize;

    /**
     * 排序
     */
    private Double sort;

    /**
     * 去向一级ID
     */
    private Long courseParentId;

    /**
     * 去向二级ID
     */
    private Long courseChildId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * IP
     */
    private String ip;

    /**
     * 端口
     */
    private String port;
    /**
     * webconsole 端口
     */
    private String servicePort;

    /**
     * 状态，1 未删除，0 禁用，-1 删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
