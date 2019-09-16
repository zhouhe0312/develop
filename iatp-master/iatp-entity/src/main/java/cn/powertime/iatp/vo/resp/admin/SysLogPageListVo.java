package cn.powertime.iatp.vo.resp.admin;

import cn.powertime.iatp.entity.SysLog;
import lombok.Data;

import java.io.Serializable;
@Data
public class SysLogPageListVo extends SysLog implements Serializable {

    private String acount;
    private String username;

}
