package cn.powertime.iatp.authserver.service.impl;

import cn.powertime.iatp.authserver.domain.SysClientDetails;
import cn.powertime.iatp.authserver.mapper.SysClientDetailsMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.List;

/**
 * @author ZYW
 * @date 2018/6/1
 */
public class ClientDetailsServiceImpl implements ClientDetailsService {

    //@Autowired
    //private StringRedisTemplate stringRedisTemplate;

    private SysClientDetailsMapper sysClientDetailsMapper;

    public ClientDetailsServiceImpl(SysClientDetailsMapper sysClientDetailsMapper) {
        this.sysClientDetailsMapper = sysClientDetailsMapper;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        QueryWrapper<SysClientDetails> wrapper = new QueryWrapper<>();
        wrapper.eq("client_id",clientId);
        wrapper.eq("status",1);
        List<SysClientDetails> list = sysClientDetailsMapper.selectList(wrapper);

        if(list == null || list.isEmpty()) {
            throw new ClientRegistrationException(clientId + "不存在！");
        }

        SysClientDetails sysClientDetails = list.get(0);
        BaseClientDetails details = new BaseClientDetails(
                sysClientDetails.getClientId(),
                sysClientDetails.getResourceIds(),
                sysClientDetails.getScope(),
                sysClientDetails.getAuthorizedGrantTypes(),
                sysClientDetails.getAuthorities());
        details.setClientSecret(sysClientDetails.getClientSecret());
        /*//后端管理员如果重新设置了token失效时间，则启用客户端设置token失效时间
        String expire_in = stringRedisTemplate.opsForValue().get("token_expire_in");
        if(StringUtils.isNotBlank(expire_in)){
            details.setAccessTokenValiditySeconds(Integer.parseInt(expire_in));
            System.out.println("令牌有效期已修改:" + details.getAccessTokenValiditySeconds());
        }*/
        return details;
    }

}
