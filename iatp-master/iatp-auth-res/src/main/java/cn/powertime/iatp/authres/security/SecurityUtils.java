package cn.powertime.iatp.authres.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * @author ZYW
 */
public final class SecurityUtils {

    public static String getCurrentUserUsername() {
        UserContext userContext = (UserContext) SecurityContextHolder.getContext();
        return userContext.getUserName();
    }

    public static Long getCurrentUserId() {
        UserContext userContext = (UserContext) SecurityContextHolder.getContext();
        return userContext.getUserId();
    }

    public static Integer getStatus() {
        SecurityContext context = SecurityContextHolder.getContext();
        if(context == null) {
            return 1;
        }
        if(context instanceof UserContext) {
            UserContext userContext = (UserContext)context;
            Integer status = userContext.getStatus();
            return status == null ? 1 : status;
        } else {
            return 1;
        }

    }
}
