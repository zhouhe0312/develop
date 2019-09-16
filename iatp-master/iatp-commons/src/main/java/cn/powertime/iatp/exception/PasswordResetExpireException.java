package cn.powertime.iatp.exception;

/**
 * @author ZYW
 * 密码过期或者被重置异常
 */
public class PasswordResetExpireException extends RuntimeException {

    public PasswordResetExpireException() {
    }

    public PasswordResetExpireException(String message) {
        super(message);
    }

    public PasswordResetExpireException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordResetExpireException(Throwable cause) {
        super(cause);
    }
}
