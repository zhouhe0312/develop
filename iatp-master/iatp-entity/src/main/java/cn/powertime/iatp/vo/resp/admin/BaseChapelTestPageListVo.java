package cn.powertime.iatp.vo.resp.admin;

import cn.powertime.iatp.entity.BaseChapelTest;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseChapelTestPageListVo extends BaseChapelTest implements Serializable {

    private String courseName;
    private String chapterPname;
    private String  chapterName;
    private Integer assessed;
}
