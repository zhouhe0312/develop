package cn.powertime.iatp.vo.resp.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : liqi
 * Date: 2018-05-18
 * Time: 10:13
 */
@Data
public class MenuTree implements Serializable {

    private String label;
    private Long id;
    private Long pid;
    private Long topId;
    private String url;
    private List<MenuTree> children;

}
