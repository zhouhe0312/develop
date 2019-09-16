package cn.powertime.iatp.vo.resp.admin;

import cn.powertime.iatp.entity.SysRes;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysResPageVo extends SysRes implements Serializable {

    private String pname;

}
