package cn.powertime.iatp.authserver.controller;

import cn.powertime.iatp.authserver.service.SysUserOauthService;
import cn.powertime.iatp.commons.AuthResVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : liqi
 * Date: 2018-11-24
 * Time: 10:23
 */
@RestController
@RequestMapping("/sysUserOauth")
public class SysUserOauthController {

    @Autowired
    private SysUserOauthService sysUserOauthService;

    @GetMapping("/getUserAndAuth")
    public AuthResVo getUserAndAuth(@RequestParam("id") Long id){
       return sysUserOauthService.getUserAndAuth(id);
    }





}
