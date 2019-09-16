package cn.powertime.iatp.vo.req.admin;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BaseTopicSearchVo implements Serializable {

    private String topicStem;

    private Long typeId;

    //@NotNull(message = "搜索类型不能为空，1：列表查询 2：组卷查询")
    private Integer selectType = 1;

}
