package cn.powertime.iatp.vo.resp.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ElementTreeVo implements Serializable {

    private List<Long> checkedKeys;

    private List<MenuTree> tree;

}
