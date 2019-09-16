package cn.powertime.iatp.vo.req.admin;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BaseTopicTypeSearchVo implements Serializable {

    private String title;

}
