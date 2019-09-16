package cn.powertime.iatp.commons;

/**
 * @author zhuyanwei
 */
public class Constants {

    public static final Long SYS_ADMIN_CUSTOMER_ID = 0L;

    /**
     * 文件路径分隔符，windows
     */
    public static final String PATH_SEPARATOR_WIN = "\\";
    /**
     * 文件路径分隔符 linux
     */
    public static final String PATH_SEPARATOR_LINUX = "/";


    /**
     * 状态正常
     */
    public static final int STATUS_NORMAL = 1;
    /**
     * 状态删除
     */
    public static final int STATUS_DEL = -1;

    /**
     * 用户状态 密码被重置
     */
    public static final int STATUS_RESET_PWD = 3;
    /**
     * 用户密码过期状态
     */
    public static final int STATUS_PWD_EXPIRE = 4;
    /**
     * 状态禁用
     */
    public static final int STATUS_DISABLED = 0;

    /**
     * 可删除
     */
    public static final int ALLOW_DEL = 1;
    /**
     * 禁止删除
     */
    public static final int NO_ALLOW_DEL = 2;

    /**
     * 系统自动用户
     */
    public static final String SYSTEM_AUTO_USER = "system";

    public static final String DOT = ".";
}
