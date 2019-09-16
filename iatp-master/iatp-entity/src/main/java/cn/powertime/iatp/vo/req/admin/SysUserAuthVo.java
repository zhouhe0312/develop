package cn.powertime.iatp.vo.req.admin;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SysUserAuthVo {
    /**
     * 角色ids
     */
    @NotBlank(message = "角色不能为空")
    private String roleIds;

    @NotNull(message = "用户ID不能为空")
    private Long id;
}
