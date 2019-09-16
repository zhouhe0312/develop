package cn.powertime.iatp.vo.req.admin;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
public class SysRoleAuthVo {
    /**
     * 权限ids
     */
    @NotBlank(message = "权限不能为空")
    private String resIds;

    @NotNull(message = "角色ID不能为空")
    private Long id;
}
