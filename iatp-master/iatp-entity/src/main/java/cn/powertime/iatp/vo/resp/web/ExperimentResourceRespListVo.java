package cn.powertime.iatp.vo.resp.web;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ExperimentResourceRespListVo implements Serializable {

    private Long id;
    private String name;
    private Long pid;
    private Long fileId;
    /**
     * 1:小节
     * 2：资源
     * 3:随堂测
     */
    private Integer type;
    /**
     * 0：小节
     * 1：视频
     * 2：ppt
     * 3：pdf
     * 4：实训
     * 5：随堂测
     */
    private Integer resourceType;
    private BigDecimal sort;

    private String username;

    private String password;

    private String ip;

    private String port;

    private String servicePort;

    private Integer defaults;

    private Long resultId;

    private String fileExtensions;


    private List<ExperimentResourceRespListVo> child;

}
