package cn.powertime.iatp.vo.resp.admin;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ZYW
 */
@Data
public class UserCollectRespListVo implements Serializable {
    /**
     * 收藏ID
     */
    private Long id;
    /**
     * 帐号
     */
    private String acount;
    /**
     * 姓名
     */
    private String username;
    /**
     * 试卷名称
     */
    private String title;
    /**
     * 试卷类型
     */
    private String testType;
    /**
     * 所属课程/实验
     */
    private String courseName;
    /**
     * 得分
     */
    private String score;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
