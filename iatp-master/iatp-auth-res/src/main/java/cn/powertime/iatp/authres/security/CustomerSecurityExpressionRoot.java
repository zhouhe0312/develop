package cn.powertime.iatp.authres.security;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;

public class CustomerSecurityExpressionRoot extends SecurityExpressionRoot {
    public CustomerSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }
}
