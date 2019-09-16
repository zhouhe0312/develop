package cn.powertime.iatp.vo.req.admin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ZYW
 */
@Data
public class UserLearningRecordReqListVo implements Serializable {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 实验/课程ID
     */
    private Long courseId;
}
