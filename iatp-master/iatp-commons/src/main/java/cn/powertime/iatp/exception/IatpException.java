package cn.powertime.iatp.exception;

public class IatpException extends RuntimeException {

    private Integer code = 0;

    public Integer getCode() {
        return code;
    }

    public IatpException() {
    }

    public IatpException(String message) {
        super(message);
    }

    public IatpException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public IatpException(String message, Throwable cause) {
        super(message, cause);
    }

    public IatpException(String message, Throwable cause,Integer code) {
        super(message, cause);
        this.code = code;
    }

    public IatpException(Throwable cause) {
        super(cause);
    }

    public IatpException(Throwable cause,Integer code) {
        super(cause);
        this.code = code;
    }
}
