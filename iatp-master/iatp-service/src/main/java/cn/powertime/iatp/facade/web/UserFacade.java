package cn.powertime.iatp.facade.web;

import cn.powertime.iatp.entity.SysUser;
import cn.powertime.iatp.vo.req.web.UserRegisterVo;
import cn.powertime.iatp.vo.req.web.UserUpdatePwdVo;
import cn.powertime.iatp.vo.req.web.UserUpdateVo;

public interface UserFacade {

    boolean checkUserNameOnly(String userName, Long id);

    boolean register(UserRegisterVo registerVo);

    SysUser details(Long id);

    boolean updatePwd(UserUpdatePwdVo vo);

    boolean updateUser(UserUpdateVo vo);

    /**
     * 校验密码是否满足系统要求
     *
     * @param password
     * @return true/false
     */
    boolean passwordCheck(String password);

    /**
     * 查询密码强度提示语
     *
     * @return 密码强度得分
     */
    String passwordTips();

}
