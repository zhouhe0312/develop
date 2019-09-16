package cn.powertime.iatp.vo.resp.admin;

import cn.powertime.iatp.entity.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : liqi
 * Date: 2018-11-29
 * Time: 11:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRolePageListVo extends SysRole {

    private String cname;

}
