package cn.powertime.iatp.vo.resp.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DeptTree implements Serializable {

    private Long id;

    private String label;

    private List<DeptTree> children;

}
