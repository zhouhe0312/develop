package cn.powertime.iatp.vo.req.admin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ZYW
 */
@Data
public class WrongQuestionsReqListVo implements Serializable {
    private String userName;

    private Long courseId;

    private Long chapelTestId;
}
