package cn.powertime.iatp.authres.fegin;

import cn.powertime.iatp.commons.AuthResVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liqi
 * @Description: 发送消息
 * @date 2018/6/19/01915:09
 */
@FeignClient("auth-server")
public interface FeginSysUserService {

    /**
     * 消息发送fegin
     */
    @GetMapping("/sysUserOauth/getUserAndAuth")
    AuthResVo getByUid(@RequestParam("id") Long id);
}
