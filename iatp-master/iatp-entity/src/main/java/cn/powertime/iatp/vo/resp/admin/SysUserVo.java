package cn.powertime.iatp.vo.resp.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author ZYW
 * @since 2018-05-03
 */
@Data
public class SysUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long id;
	private String acount;
	//private String password;
	private String username;
	private String email;
    /**
     * 1:男，0:女
     */
	private Integer sex;
    /**
     * 电话
     */
	private String phone;
    /**
     * 头像
     */
	private String avatar;
    /**
     * 1 可用 0 不可用 -1: 删除
     */
	private Integer status;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	private String roleName;
	private String deptName;
	private Long roleId;
	private String nickname;
	private Long unitId;


}
