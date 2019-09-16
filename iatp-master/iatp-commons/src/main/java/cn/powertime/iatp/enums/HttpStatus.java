package cn.powertime.iatp.enums;

/**
 * @author ZYW
 */

public enum HttpStatus {

    OK(200, "请求成功"),
    BAD_REQUEST(400, "请求出错"),
    UNAUTHORIZED(401, "没有登录"),
    FORBIDDEN(403, "没有权限"),
    NOT_FOUND(404, "找不到页面"),
    INTERNAL_SERVER_ERROR(500, "服务器出错了"),
    LOGIN_FAIL(1001,"登录异常！"),
    TOKEN_PARSER_FAIL(1002,"令牌解析异常！"),

    VALIDATED_FAIL(1004,"数据验证失败！"),
    RESET_PASSWORD(1005,"密码被重置了！"),

    CHECK_FAIL(10000,"验证失败！"),

    SUB_NOTIFY_FAIL(2017,"通知订阅失败"),

    CMD_EXECUTE_FAIL(2018,"命令执行失败"),

    //云平台操作
    GAS_DEVICE_NOT_ACTIVE(100610,"设备未激活");


    private final int value;

    private final String msg;

    HttpStatus(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public int value() {
        return value;
    }

    public String msg() {
        return msg;
    }
}
