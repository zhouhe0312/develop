package cn.powertime.iatp.vo.req.admin;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseChapelTestSearchVo implements Serializable {

    private String title;

    private Integer testType;

}
