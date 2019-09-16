package cn.powertime.iatp.authserver.security;

import cn.powertime.iatp.authserver.config.UserLockConfig;
import cn.powertime.iatp.authserver.domain.SysSafeStrategy;
import cn.powertime.iatp.authserver.domain.SysUser;
import cn.powertime.iatp.authserver.exception.CustomAuthenticationException;
import cn.powertime.iatp.authserver.exception.ErrorCode;
import cn.powertime.iatp.authserver.mapper.SysUserOauthMapper;
import cn.powertime.iatp.authserver.service.SysRoleService;
import cn.powertime.iatp.authserver.service.SysSafeStrategyService;
import cn.powertime.iatp.authserver.util.OauthConstants;
import cn.powertime.iatp.commons.Constants;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static cn.powertime.iatp.authserver.util.OauthConstants.CLIENT_ID_MOBILE;
import static cn.powertime.iatp.authserver.util.OauthConstants.CLIENT_ID_VERIFICATION;

/**
 * @author zhuyanwei
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SysUserOauthMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SysSafeStrategyService sysSafeStrategyService;

    @Autowired
    private UserLockConfig userLockConfig;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password;
        Map data = (Map) authentication.getDetails();
        String clientId = (String) data.get("client");
        Assert.hasText(clientId, "clientId must have value");
        String type = (String) data.get("type");
        Map<String, String> map;

        password = (String) authentication.getCredentials();
        //如果你是调用user服务，这边不用注掉
        //map = userClient.checkUsernameAndPassword(getUserServicePostObject(username, password, type));
        //map = checkUsernameAndPassword(getUserServicePostObject(username, password, type));
        if (StringUtils.equals(clientId, CLIENT_ID_MOBILE)
                || StringUtils.equals(clientId, CLIENT_ID_VERIFICATION)) {
            map = checkUserNameAndPwdOrCode(username, password, clientId);
        } else {
            map = checkUsernameAndPassword(username, password, clientId);
        }


        String userId = map.get("userId");
        if (StringUtils.isBlank(userId)) {
            String errorCode = map.get("code");
            throw new BadCredentialsException(errorCode);
        }

        CustomUserDetails customUserDetails = buildCustomUserDetails(username, password, userId, clientId);
        return new CustomAuthenticationToken(customUserDetails);
    }

    private CustomUserDetails buildCustomUserDetails(String username, String password, String userId, String clientId) {
        return new CustomUserDetails.CustomUserDetailsBuilder()
                .withUserId(userId)
                .withPassword(password)
                .withUsername(username)
                .withClientId(clientId)
                .build();
    }

    /*private Map<String, String> getUserServicePostObject(String username, String password, String type) {
        Map<String, String> requestParam = new HashMap<String, String>();
        requestParam.put("userName", username);
        requestParam.put("password", password);
        if (StringUtils.isNotBlank(type)) {
            requestParam.put("type", type);
        }
        return requestParam;
    }*/

    //模拟调用user服务的方法
    /*private Map checkUsernameAndPassword(Map map) {

        //checkUsernameAndPassword
        Map ret = new HashMap();
        ret.put("userId", UUID.randomUUID().toString());

        return ret;
    }*/

    private Map<String, String> checkUserNameAndPwdOrCode(String userName, String pwdOrCode, String clientId) {
        Map<String, String> result = Maps.newHashMap();

        /*QueryWrapper<AppAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile_phone",userName);

        List<AppAccount> accountList = appAccountService.list(wrapper);
        if(CollectionUtils.isEmpty(accountList)) {
            ErrorCode errorCode = new ErrorCode(100,"用户" + userName + "不存在!","用户" + userName + "不存在!");
            throw new CustomAuthenticationException(HttpStatus.BAD_REQUEST,errorCode);
        }

        if(accountList.size() > 1) {
            ErrorCode errorCode = new ErrorCode(110,"用户" + userName + "不唯一!","用户" + userName + "不唯一!");
            throw new CustomAuthenticationException(HttpStatus.BAD_REQUEST,errorCode);
        }

        AppAccount appAccount = accountList.get(0);

        if(appAccount.getStatus() != 1) {
            ErrorCode errorCode = new ErrorCode(130,"用户被限制登录！","用户被限制登录！");
            throw new CustomAuthenticationException(HttpStatus.BAD_REQUEST,errorCode);
        }

        if(StringUtils.equals(clientId,CLIENT_ID_MOBILE)) {
            if(!passwordEncoder.matches(pwdOrCode,appAccount.getPassword())){
                ErrorCode errorCode = new ErrorCode(120,"密码输入错误！","密码输入错误！");
                throw new CustomAuthenticationException(HttpStatus.BAD_REQUEST,errorCode);
            }
        } else {
            String smsCode = redisTemplate.boundValueOps(userName).get();
            if(smsCode == null) {
                ErrorCode errorCode = new ErrorCode(140,"验证码已过期！","验证码已过期！");
                throw new CustomAuthenticationException(HttpStatus.BAD_REQUEST,errorCode);
            }
            if(!StringUtils.equals(pwdOrCode,smsCode)) {
                ErrorCode errorCode = new ErrorCode(120,"短信验证码输入错误！","短信验证码输入错误！");
                throw new CustomAuthenticationException(HttpStatus.BAD_REQUEST,errorCode);
            }
        }

        result.put("userId", String.valueOf(appAccount.getId()));*/
        return result;
    }

    private Map<String, String> checkUsernameAndPassword(String username, String password, String clientId) {
        Map<String, String> result = Maps.newHashMap();

        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("acount", username);
        wrapper.ne("status", -1);

        int type = 1;

        if (StringUtils.equals(clientId, OauthConstants.CLIENT_ID_WEBCLIENT)) {
            type = 2;
        }

        wrapper.eq("type", type);

        List<SysUser> userList = sysUserMapper.selectList(wrapper);
        if (userList == null || userList.isEmpty()) {
            ErrorCode errorCode = new ErrorCode(100, "用户" + username + "不存在!", "用户" + username + "不存在!");
            throw new CustomAuthenticationException(HttpStatus.BAD_REQUEST, errorCode);
        }

        if (userList.size() > 1) {
            ErrorCode errorCode = new ErrorCode(110, "用户" + username + "不唯一!", "用户" + username + "不唯一!");
            throw new CustomAuthenticationException(HttpStatus.BAD_REQUEST, errorCode);
        }

        SysUser user = userList.get(0);
        SysSafeStrategy sysSafeStrategy = sysSafeStrategyService.getOne();

        int failCount = 0;
        Long lockTime = null;
        if (sysSafeStrategy != null) {
            failCount = sysSafeStrategy.getLoginFailTime();//获取安全策略表中的允许登陆失败次数
            lockTime = sysSafeStrategy.getLockLoginTime();//获取安全策略表中的锁定用户登陆时间（单位：秒）
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            if ("LOCK".equals(stringRedisTemplate.opsForValue().get(username + "-is-lock"))) {
                ErrorCode errorCode = new ErrorCode(130, "此用户 " + username + " 登录失败已达 " + failCount + " 次，已被限制登录，请" + (lockTime / 60) + "分钟后重试。", "此用户 " + username + " 登录失败已达 " + failCount + " 次，已被限制登录，请" + (lockTime / 60) + "分钟后重试。");
                throw new CustomAuthenticationException(HttpStatus.BAD_REQUEST, errorCode);
            }
            //将用户错误次数存放到redis中
            //Long loginLockTime = userLockConfig.getTime();
            if (stringRedisTemplate.hasKey(username + "-login-error-count") == false) {
                stringRedisTemplate.opsForValue().set(username + "-login-error-count", "1", lockTime, TimeUnit.SECONDS);
            } else {
                stringRedisTemplate.boundValueOps(username + "-login-error-count").increment(1);//val +1;
            }

            int loginErrorCount = Integer.parseInt(stringRedisTemplate.opsForValue().get(username + "-login-error-count"));
            if (loginErrorCount > failCount) {
                stringRedisTemplate.opsForValue().set(username + "-is-lock", "LOCK", lockTime, TimeUnit.SECONDS);
                ErrorCode errorCode = new ErrorCode(130, "此用户 " + username + " 登录失败已达 " + failCount + " 次，已被限制登录，请" + (lockTime / 60) + "分钟后重试。", "此用户 " + username + " 登录失败已达 " + failCount + " 次，已被限制登录，请" + (lockTime / 60) + "分钟后重试。");
                throw new CustomAuthenticationException(HttpStatus.BAD_REQUEST, errorCode);
            }
            ErrorCode errorCode = new ErrorCode(120, "用户名或密码输入错误，登录失败" + failCount + "次帐号将被锁定！", "用户名或密码输入错误，登录失败" + failCount + "次帐号将被锁定！");
            throw new CustomAuthenticationException(HttpStatus.BAD_REQUEST, errorCode);
        }

        if ("LOCK".equals(stringRedisTemplate.opsForValue().get(username + "-is-lock"))) {
            ErrorCode errorCode = new ErrorCode(130, "此用户 "+username+" 已被限制登录，请" + (lockTime / 60) + "分钟后重试。", "此用户 "+username+" 已被限制登录，请" + (lockTime / 60) + "分钟后重试。");
            throw new CustomAuthenticationException(HttpStatus.BAD_REQUEST, errorCode);
        }

        if (user.getStatus() == 0) {
            ErrorCode errorCode = new ErrorCode(100, "此用户 " + username + " 已被禁用!", "此用户 " + username + " 已被禁用！");
            throw new CustomAuthenticationException(HttpStatus.BAD_REQUEST, errorCode);
        }

        //密码有效期已过
        LocalDateTime modPasswdDate = user.getModPasswdDate();
        Long pwdValidTime = sysSafeStrategy.getPwdValidTime();
        LocalDateTime expireDate = modPasswdDate.plusDays(pwdValidTime);
        if (LocalDateTime.now().isAfter(expireDate)) {
            user.setStatus(Constants.STATUS_PWD_EXPIRE);
            user.setUpdateTime(LocalDateTime.now());
            sysUserMapper.updateById(user);
        }

        stringRedisTemplate.delete(username + "-login-error-count");//根据key删除缓存
        stringRedisTemplate.delete(username + "-is-lock");//根据key删除缓存
        result.put("userId", String.valueOf(user.getId()));
        sysRoleService.inserLoginLog(user);
        return result;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
