package cn.powertime.iatp.vo.resp.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CommonTree implements Serializable {

    private Long id;

    private Long pid;

    private String label;

    private String desc;

    private List<CommonTree> children;

}
