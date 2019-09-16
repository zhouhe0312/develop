package cn.powertime.iatp.vo.resp.web;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
@Data
@Builder
public class SubmitPaperRespVo implements Serializable {

    private Integer score;

    private Long resultId;

    private Long id;

}
