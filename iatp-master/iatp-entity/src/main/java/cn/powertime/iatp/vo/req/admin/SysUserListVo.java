package cn.powertime.iatp.vo.req.admin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liqi
 * @Description:
 * @date 2018/5/4/00410:40
 */
@Data
public class SysUserListVo implements Serializable {

    private String phone;
    private String username;
    private String acount;
    private Integer type;

}


