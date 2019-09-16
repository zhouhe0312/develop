package cn.powertime.iatp.authserver.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @author zhuyanwei
 */
public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private CustomUserDetails userDetails;

    public CustomAuthenticationToken(CustomUserDetails userDetails) {
        super(null);
        this.userDetails = userDetails;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.userDetails.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return this.userDetails;
    }
}
