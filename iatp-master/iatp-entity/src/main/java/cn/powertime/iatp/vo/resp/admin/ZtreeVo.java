package cn.powertime.iatp.vo.resp.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : liqi
 * Date: 2018-05-08
 * Time: 10:07
 */
public class ZtreeVo implements Serializable {
    private Long id;
    private Long pid;
    private String name;
    private Boolean isMenu;
    private Boolean checked;

    public ZtreeVo(Long id, Long pid, String name, Boolean isMenu, Boolean checked){
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.isMenu = isMenu;
        this.checked = checked;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Boolean isMenu) {
        this.isMenu = isMenu;
    }
}
