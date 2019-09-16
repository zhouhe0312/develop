package cn.powertime.iatp.facade.admin;

import cn.powertime.iatp.entity.SysUser;
import cn.powertime.iatp.vo.req.admin.*;
import cn.powertime.iatp.vo.resp.admin.SysUserVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ZYW
 * @since 2019-04-16
 */
public interface SysUserFacade {

    boolean add(SysUserAddVo addVo,String pwd);

    boolean checkUserNameOnly(String userName, Long id);

    boolean edit(SysUserEditVo editVo);
    boolean accountUpdate(SysUserUpdateReqVo editVo);

    boolean del(Long id);

    Page<SysUserVo> list(ParamPageVo<SysUserListVo> vo);

    /**
     * 修改密码
     * @param vo
     * @return
     */
    boolean updatePwd(SysUserUpdatePwdVo vo,String newPwd);

    /**
     * 重置密码
     * @param vo
     * @return
     */
    boolean resetPwd(SysUserResetPwdReqVo vo);

    SysUser details(Long id);

    boolean auth(SysUserAuthVo authVo);

    boolean batchDel(List<String> strings);
}
