package cn.powertime.iatp.vo.resp.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class KnowLedgeVo implements Serializable {

    private Long id;

    private String title;

    private String content;

    private String issued;

    private String version;

    private String releaseDate;

    private String msg;

    private Long subid;

}
