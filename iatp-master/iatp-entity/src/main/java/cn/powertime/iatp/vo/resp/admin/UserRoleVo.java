package cn.powertime.iatp.vo.resp.admin;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserRoleVo implements Serializable {

    private Long roleId;

    private String Name;

    private boolean checked;

}
