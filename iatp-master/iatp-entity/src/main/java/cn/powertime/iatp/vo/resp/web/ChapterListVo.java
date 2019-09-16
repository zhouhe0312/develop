package cn.powertime.iatp.vo.resp.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ChapterListVo implements Serializable {

    private Long id;
    private Long pid;
    private String name;
    /**
     * 0:未学习
     * 1：学习中
     * 2:学习完成
     */
    private Integer studyStatus;

    private Integer type;
    @JsonIgnore
    private String tempStatus;
    @JsonIgnore
    private Integer num;

    private List<ChapterListVo> child;

}
